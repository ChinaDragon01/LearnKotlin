package com.test.learnkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.test.learnkotlin.base.BaseViewModel
import com.test.learnkotlin.model.LoginModel
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel<LoginModel>(application) {

    fun login(registerPhone: String, verifyCode: String, x: String, y: String) {
        viewModelScope.launch {
            val responseEntity = model.login(registerPhone, verifyCode, x, y)

        }
    }

}