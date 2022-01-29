package com.test.learnkotlin.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.test.learnkotlin.utils.ReflexUtil

open class BaseViewModel<Model : BaseModel>(application: Application) :
    AndroidViewModel(application) {
    open val model: Model by lazy(LazyThreadSafetyMode.NONE) {
        ReflexUtil.getTargetClass(this, IModel::class.java) as Model
    }

}