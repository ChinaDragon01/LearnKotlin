package com.test.learnkotlin.base

interface IActivity<ViewModel : BaseViewModel<*>> {
    fun initView()
    fun initData()
    fun initEvent()
    fun variableId(): Int
    fun getViewModel(): ViewModel

}