package com.test.learnkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.test.learnkotlin.BR
import com.test.learnkotlin.R
import com.test.learnkotlin.bean.Person
import com.test.learnkotlin.widget.DataBindingViewHolder

class CommonAdapter : RecyclerView.Adapter<DataBindingViewHolder<ViewDataBinding>>() {
    val personList = mutableListOf<Person>()

    init {
        for (i in 0..20) {
            personList.add(Person("姓名 " + i))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<ViewDataBinding> {
        return DataBindingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_common, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<ViewDataBinding>, position: Int) {
        holder.binding.setVariable(BR.mPerson, personList.get(position))
    }

    override fun getItemCount(): Int = personList.size


}