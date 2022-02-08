package com.test.learnkotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.learnkotlin.BR
import com.test.learnkotlin.R
import com.test.learnkotlin.base.BaseActivity
import com.test.learnkotlin.databinding.ActivityMainBinding
import com.test.learnkotlin.viewmodel.MainViewModel

/*
将 Kotlin 协程与生命周期感知型组件一起使用
https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope
添加 KTX 依赖项
本主题中介绍的内置协程范围包含在每个相应组件的 KTX 扩展程序中。请务必在使用这些范围时添加相应的依赖项。

对于 ViewModelScope，请使用 androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0 或更高版本。
对于 LifecycleScope，请使用 androidx.lifecycle:lifecycle-runtime-ktx:2.2.0 或更高版本。
对于 liveData，请使用 androidx.lifecycle:lifecycle-livedata-ktx:2.2.0 或更高版本。


https://developer.android.com/kotlin/ktx
Android KTX 是包含在 Android Jetpack 及其他 Android 库中的一组 Kotlin 扩展程序。KTX 扩展程序可以为 Jetpack、Android 平台及其他 API 提供简洁的惯用 Kotlin 代码。为此，这些扩展程序利用了多种 Kotlin 语言功能，其中包括：

扩展函数
扩展属性
Lambda
命名参数
参数默认值
协程



使用生命周期感知型组件处理生命周期
https://developer.android.com/topic/libraries/architecture/lifecycle


Android 上的 Kotlin 协程
https://developer.android.com/kotlin/coroutines#executing-in-a-background-thread


Lifecycle
https://developer.android.google.cn/jetpack/androidx/releases/lifecycle


 Kotlin 注解处理 https://www.kotlincn.net/docs/reference/kapt.html


 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initEvent() {
        super.initEvent()
    }

    override fun variableId(): Int = BR.mMainViewModel

    override fun getContentLayoutId(): Int = R.layout.activity_main
}