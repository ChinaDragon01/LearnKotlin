package com.test.learnkotlin.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.learnkotlin.R
import com.test.learnkotlin.adapter.CommonAdapter
import com.test.learnkotlin.base.BaseViewModel
import com.test.learnkotlin.http.ResponseEntity
import com.test.learnkotlin.model.LoginModel
import com.test.learnkotlin.utils.LogUitls
import com.test.learnkotlin.utils.SingleLiveEvent
import com.test.learnkotlin.utils.ToastUtil
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel<LoginModel>(application) {
    val smsCodeLiveEvent = SingleLiveEvent<Boolean>()
    val paddingBottomValue = ObservableInt()
    val backgroundResId = ObservableInt(R.drawable.shape_bg_color_e2e2e2)
    val commonAdapter = ObservableField<CommonAdapter>()


    fun login(registerPhone: String, verifyCode: String, x: String = "0", y: String = "0") {
        viewModelScope.launch {
            val responseEntity = model.login(registerPhone, verifyCode, x, y)
            LogUitls.i(responseEntity.toString())
        }
    }

    fun smscode2(
        captchaType: String = "REGISTER",
        mobileNo: String,
    ) {
        launchHttp(
            { model.smsCode(captchaType, mobileNo) }, {
                when {
                    it.isSuccessful() -> {
                        LogUitls.i(it.toString())
                    }

                    it.isFailure() -> {
                        it.message?.let { it1 -> ToastUtil.show(it1) }
                    }

                    it.isException() -> {
                        ToastUtil.show(it.exception!!.message!!)
                    }
                }
            }, showLoading = true
        )
    }

    fun smsCode(
        captchaType: String = "REGISTER",
        mobileNo: String,
        x: Int? = null,
        y: Int? = null
    ) {
        viewModelScope.launch {
            val responseEntity = model.smsCode(captchaType, mobileNo)
            LogUitls.i(responseEntity.toString())
        }
    }

    fun performViewClicks(view: View) {
        val viewId = view.id
        when (viewId) {
            R.id.tv_sms_code -> {
                paddingBottomValue.set(500)
                smsCodeLiveEvent.postValue(true)
                backgroundResId.set(R.drawable.shape_bg_black)
                LogUitls.i("paddingBottomValue = ${paddingBottomValue.get()}")
            }
        }
    }
}