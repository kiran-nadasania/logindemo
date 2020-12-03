package com.promptsoftech.logindemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promptsoftech.logindemo.models.LoginResponse
import com.promptsoftech.logindemo.repositories.UserRepository
import com.promptsoftech.logindemo.requests.LoginRequest
import com.promptsoftech.logindemo.utils.ErrorMessage
import com.promptsoftech.logindemo.utils.Resource
import retrofit2.Response

class LoginModel(private val userRepository: UserRepository) : ViewModel(){

    var username = MutableLiveData("")
    var password = MutableLiveData("")
    val data : MutableLiveData<Any>? by lazy {
        MutableLiveData<Any>()
    }

    val formErrors : MutableLiveData<ErrorMessage>? by lazy {
        MutableLiveData<ErrorMessage>()
    }

    fun login(loginRequest: LoginRequest){
        data?.value = Resource.Loading<Boolean>(true)
        userRepository.getUser(loginRequest,
            {user: Response<LoginResponse> ->
                data?.value = Resource.Loading<Boolean>(false)
                data?.value = Resource.Success(user)
                //userRepository.saveUser(UserEntity(user.body()!!.user.userId,user.body()!!.user.userName,"wewe"))
            },
            {t: Throwable ->   data?.value = Resource.Error<String>(t.toString())})
    }

    fun isFormValidMain() : Boolean{
        return when {
            username.value!!.isEmpty() -> {
                formErrors?.postValue(ErrorMessage("username","Please enter username"))
                false
            }
            password.value!!.isEmpty() -> {
                formErrors?.postValue(ErrorMessage("password","Please enter password"))
                false
            }
            else -> {
                formErrors?.postValue(ErrorMessage("",""))
                true
            }
        }
    }

}