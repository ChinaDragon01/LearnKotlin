package com.test.learnkotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.learnkotlin.utils.ReflexUtil2

abstract class BaseActivity<ViewModel : BaseViewModel<*>, DataBinding : ViewDataBinding> :
    AppCompatActivity(),
    IActivity<ViewModel> {
    private var mViewModel: ViewModel? = null
    open lateinit var mDataBinding: DataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initView()
        initData()
        initEvent()
    }

    private fun initViewModel() {
        mViewModel = obtainViewModel()
        mDataBinding = DataBindingUtil.setContentView(this, getContentLayoutId())
        mDataBinding?.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(variableId(), mViewModel)
        }
    }

    fun obtainViewModel(): ViewModel? {

        val targetClass = ReflexUtil2.getTargetClass(this, AndroidViewModel::class.java)
        if (targetClass != null) {
            return ViewModelProvider(this).get(targetClass) as ViewModel
        }

        return null
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun variableId(): Int = 0

    open fun getContentLayoutId(): Int = 0

    override fun getViewModel(): ViewModel {
        return mViewModel!!
    }
}