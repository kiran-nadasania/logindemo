package com.promptsoftech.logindemo.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.promptsoftech.logindemo.MyApplication
import com.promptsoftech.logindemo.R
import com.promptsoftech.logindemo.base.BaseActivity
import com.promptsoftech.logindemo.database.Database
import com.promptsoftech.logindemo.database.entity.UserEntity
import com.promptsoftech.logindemo.factory.MainViewModelFactory
import com.promptsoftech.logindemo.injection.component.DaggerAppComponent
import com.promptsoftech.logindemo.models.LoginResponse
import com.promptsoftech.logindemo.viewmodels.LoginModel
import com.promptsoftech.logindemo.requests.LoginRequest
import com.promptsoftech.logindemo.utils.AppUtils
import com.promptsoftech.logindemo.utils.RESPONSE_FAILED
import com.promptsoftech.logindemo.utils.RESPONSE_SUCCESS
import com.promptsoftech.logindemo.utils.Resource
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.jetbrains.anko.toast
import retrofit2.Response
import javax.inject.Inject


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginModel

    //for local room database
    private var db : Database? = null

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val component: DaggerAppComponent = (this.application as MyApplication).getComponent()
        component.inject(this)

        db = Database.getDatabase(this)

        viewModel = ViewModelProviders.of(this, factory).get(LoginModel::class.java)

        initViews()

        //watch on observer for login task
        watchObserver()
    }

    fun initViews(){
        btn_login.setOnClickListener(this)

        edt_username.doAfterTextChanged { text ->  viewModel.username =
            MutableLiveData(text?.toString())
            if(text?.isNotEmpty()!!){
                input_username.isErrorEnabled = false
            }
        }
        edt_password.doAfterTextChanged { text ->  viewModel.password =
            MutableLiveData(text?.toString())
            if(text?.isNotEmpty()!!){
                input_password.isErrorEnabled = false
            }
        }
    }

    fun watchObserver(){
        //observer for login api
        viewModel.data?.observe(this, Observer<Any> {
            when (it) {
                is Resource.Error<*> -> {
                    if (it.message!!.isNotEmpty()) {
                        toast(it.message.toString())
                    }
                }
                is Resource.Success<*> -> {

                    try {
                        it.data as Response<LoginResponse>
                        when (it.data.body()!!.errorCode) {
                            RESPONSE_SUCCESS -> {

                                toast(getString(R.string.login_success))

                                //for get X-Acc header from response
                                val headerList: Headers = it.data.headers()
                                var xaccHeader:String = ""
                                for (header in headerList.toMultimap()) {
                                    if(header.key == "x-acc"){
                                        xaccHeader = header.value[0]
                                    }
                                }

                                //save user to local database
                                GlobalScope.launch(Dispatchers.Main){
                                    try {
                                        db?.userDao()
                                            ?.insertUser(
                                                UserEntity(it.data!!.body()!!.user.userId,
                                                it.data!!.body()!!.user.userName,
                                                xaccHeader)
                                            )
                                    } catch (e: Exception) {
                                        toast(getString(R.string.error_insert_data))
                                    }
                                }
                                pb_login.visibility = View.GONE
                            }
                            RESPONSE_FAILED -> {
                                toast(it.data.body()!!.errorMessage)
                            }
                        }
                    } catch (e: Exception) {
                        toast(getString(R.string.api_failed))
                    }

                }
                is Resource.Loading<*> -> {
                    if (it.isLoadingShow!!) {
                        pb_login.visibility = View.VISIBLE
                    } else {
                        pb_login.visibility = View.GONE
                    }
                }
            }
        })

        //observer for error message
        viewModel.formErrors?.observe(this, Observer {
            if (it.field.isNotEmpty()) {
                when (it.field) {
                    "username" -> {
                        input_username.error = it.message
                    }
                    "password" -> {
                        input_password.error = it.message
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> {
                if(AppUtils.isOnline()){
                    if(viewModel.isFormValidMain()){
                        viewModel.login(LoginRequest(edt_username.getValue(),edt_password.getValue()))
                    }
                }else{
                    toast(getString(R.string.no_internet))
                }
            }
        }
    }
}