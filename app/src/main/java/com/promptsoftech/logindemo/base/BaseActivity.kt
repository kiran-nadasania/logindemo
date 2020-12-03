package com.promptsoftech.logindemo.base

import android.os.Bundle
import android.os.Message
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.toast

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String) = toast(message)

    fun EditText.getValue():String = this.text.toString().trim()

}