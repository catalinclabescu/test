package com.catalin.library

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecyclerAdapter<V: ViewDataBinding, M>(@LayoutRes val layoutId: Int): RecyclerView.Adapter<ViewHolder<V>>() {
    var items: ArrayList<M>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false))
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        setupView(position, holder.binding, items!![position])
        holder.binding.root.setOnClickListener {
            onItemSelected(position, holder.binding, items!![position])
        }
    }

    fun setData(list: ArrayList<M>) {
        items = list
        notifyDataSetChanged()
    }

    fun addItem(item: M) {
        items?.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(item: M) {
        if(items?.contains(item)!!) {
            items?.remove(item)
            notifyDataSetChanged()
        }

    }
    abstract fun setupView(position: Int, binding:V, data: M)

    abstract fun onItemSelected(position: Int, binding: V, data: M)
}