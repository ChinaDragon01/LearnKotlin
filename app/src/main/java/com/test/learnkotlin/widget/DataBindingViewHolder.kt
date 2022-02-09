package com.test.learnkotlin.widget

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T : ViewDataBinding>(itemview: View) : RecyclerView.ViewHolder(itemview) {
    val binding = DataBindingUtil.bind<T>(itemview)!!
}