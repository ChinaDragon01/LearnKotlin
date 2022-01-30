package com.test.learnkotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.learnkotlin.utils.ReflexUtil

abstract class BaseActivity<ViewModel : BaseViewModel<*>> : AppCompatActivity(), IActivity {
    private var mViewModel: ViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayoutId())
        initViewModel()
        initView()
        initData()
    }

    private fun initViewModel() {
        obtainViewModel()
    }

    fun obtainViewModel(): ViewModel? {

        val targetClass = ReflexUtil.getTargetClass(this, AndroidViewModel::class.java)
        if (targetClass != null) {
            return ViewModelProvider(this).get(targetClass) as ViewModel
        }

        return null
    }

    override fun initView() {
    }

    override fun initData() {
    }

   open fun getContentLayoutId(): Int = 0
}