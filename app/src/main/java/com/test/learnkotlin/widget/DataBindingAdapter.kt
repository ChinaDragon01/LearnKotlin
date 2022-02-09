package com.test.learnkotlin.widget

import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("backgroundResId")
    fun setbackgroundResId(viewGroup: ViewGroup, @DrawableRes resId: Int) {
        if (resId == 0) {
            viewGroup.background = null
        } else {
            viewGroup.setBackgroundResource(resId)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["android:paddingLeft", " android:paddingTop", "android:paddingRight", "android:paddingBottom"],
        requireAll = false
    )
    fun setPadding(
        viewGroup: ViewGroup,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0
    ) {
        viewGroup.setPadding(left, top, right, bottom)
    }

//    @JvmStatic
//    @BindingAdapter("android:paddingLeft")
//    fun setPaddingLeft(viewGroup: ViewGroup, left: Int = 0) {
//        viewGroup.setPadding(
//            left, viewGroup.paddingTop, viewGroup.paddingRight, viewGroup.paddingBottom
//        )
//    }


    @JvmStatic
    @BindingAdapter("adapter")
//    fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {//http://www.javashuo.com/article/p-pveedber-kh.html
    fun setRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

}