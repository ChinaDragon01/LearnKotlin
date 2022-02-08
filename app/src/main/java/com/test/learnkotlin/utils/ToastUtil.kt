package com.test.learnkotlin.utils

import android.widget.Toast
import com.test.learnkotlin.app.App

object ToastUtil {
    fun show(text: String) {
        Toast.makeText(App.appContext, text!!, Toast.LENGTH_SHORT).show()
    }


}