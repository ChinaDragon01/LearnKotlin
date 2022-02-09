package com.test.learnkotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.learnkotlin.utils.ReflexUtil2
import com.test.learnkotlin.utils.ToastUtil

abstract class BaseActivity<ViewModel : BaseViewModel<*>, DataBinding : ViewDataBinding> :
    AppCompatActivity(),
    IActivity<ViewModel> {
    open lateinit var mViewModel: ViewModel
    open lateinit var mDataBinding: DataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initView()
        initData()
        initEvent()
    }

    private fun initViewModel() {
        mViewModel = obtainViewModel()!!
        mViewModel?.apply {
            loadingEvent.observe(this@BaseActivity,{
                if (it == true){
                    ToastUtil.show("加载中...")
                }
            })
        }
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