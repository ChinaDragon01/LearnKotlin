package com.test.learnkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.test.learnkotlin.base.BaseViewModel
import com.test.learnkotlin.model.MainModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel<MainModel>(application) {
    /*
        ViewModelScope
        为应用中的每个 ViewModel 定义了 ViewModelScope。如果 ViewModel 已清除，则在此范围内启动的协程都会自动取消。
        如果您具有仅在 ViewModel 处于活动状态时才需要完成的工作，此时协程非常有用。例如，如果要为布局计算某些数据，
        则应将工作范围限定至 ViewModel，以便在 ViewModel 清除后，系统会自动取消工作以避免消耗资源。

        您可以通过 ViewModel 的 viewModelScope 属性访问 ViewModel 的 CoroutineScope，如以下示例所示：
     */

    init {
        viewModelScope.launch {

        }
    }

}