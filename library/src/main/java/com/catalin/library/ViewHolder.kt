package com.catalin.library

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<V: ViewDataBinding>(val binding: V): RecyclerView.ViewHolder(binding.root)