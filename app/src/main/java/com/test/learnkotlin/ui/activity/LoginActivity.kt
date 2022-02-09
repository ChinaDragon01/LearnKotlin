package com.test.learnkotlin.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.test.learnkotlin.BR
import com.test.learnkotlin.R
import com.test.learnkotlin.adapter.CommonAdapter
import com.test.learnkotlin.base.BaseActivity
import com.test.learnkotlin.databinding.ActivityLoginBinding
import com.test.learnkotlin.utils.ToastUtil
import com.test.learnkotlin.viewmodel.LoginViewModel

/*
    Kotlin 注解处理 https://www.kotlincn.net/docs/reference/kapt.html

    Android Studio Kotlin databinding: Unresolved reference on BR       https://stackoverflow.com/questions/57983508/android-studio-kotlin-databinding-unresolved-reference-on-br

    apply plugin: 'kotlin-kapt' in app build.gradle.

    https://blog.csdn.net/weixin_40929353/article/details/102911137

 */

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    var et_mobile: EditText? = null
    var et_sms_code: EditText? = null
    override fun getContentLayoutId(): Int = R.layout.activity_login

    override fun variableId(): Int = BR.mLoginViewModel

    override fun initView() {
        super.initView()
        et_mobile = mDataBinding.etMobile
        et_sms_code = mDataBinding.etSmscode

    }

    override fun initData() {
        super.initData()
        mViewModel.commonAdapter.set(CommonAdapter())
    }

    override fun initEvent() {
        super.initEvent()
        getViewModel().smsCodeLiveEvent.observe(this, {
            var mobile: String = et_mobile?.text.toString()
            if (mobile.isNullOrEmpty() || mobile.length != 10) {
                ToastUtil.show("请输入正确的手机号")
                return@observe
            }

            mViewModel.smscode2("REGISTER", mobile)

        })

//        findViewById<TextView>(R.id.tv_sms_code).setOnClickListener {
//            var mobile: String = et_mobile?.text.toString()
//            if (mobile.isNullOrEmpty() || mobile.length != 10) {
//                ToastUtil.show("请输入正确的手机号")
//                return@setOnClickListener
//            }
//
//            getViewModel()?.smsCode("REGISTER", mobile)
//
//        }

        findViewById<TextView>(R.id.tv_login).setOnClickListener {
            var mobile: String = et_mobile?.text.toString()

            if (mobile.isNullOrEmpty() || mobile.length != 10) {
                ToastUtil.show("请输入正确的手机号")
                return@setOnClickListener
            }

            var smsCode: String = et_sms_code?.text.toString()

            if (smsCode.isNullOrEmpty() || smsCode.length != 4) {
                ToastUtil.show("请输入正确的验证码")
                return@setOnClickListener
            }

            getViewModel().login(mobile, smsCode)

        }

    }
}