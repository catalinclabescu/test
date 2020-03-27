package com.catalin.test.ui.main

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catalin.library.BindingRecyclerAdapter
import com.catalin.library.Message
import com.catalin.test.R
import com.catalin.test.databinding.RowMessageBinding
import com.squareup.picasso.Picasso

class ApiListViewModel : ViewModel(), Observable {
    private val callbacks = PropertyChangeRegistry()
    val loading: ObservableBoolean = ObservableBoolean(false)
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    fun updateMessages(messages: List<Message>) {
        loading.set(false)
        adapter.setData(ArrayList(messages))
        //notifyChange()
    }
    val adapter: BindingRecyclerAdapter<RowMessageBinding, Message> = object : BindingRecyclerAdapter<RowMessageBinding, Message>(
        R.layout.row_message) {
        override fun setupView(position: Int, binding: RowMessageBinding, data: Message) {
            binding.message = data
            val imageRoot = "https://www.weightwatchers.com${data.image}"
            Picasso.get().load(Uri.parse(imageRoot)).fit().noFade().placeholder(R.mipmap.ic_launcher).into(binding.icon)
        }

        override fun onItemSelected(position: Int, binding: RowMessageBinding, data: Message) {
            
        } 

    }

    companion object {
        @BindingAdapter(value = ["android:adapter"])
        @JvmStatic
        fun setupRecyclerAdapter(v: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            v.adapter = adapter
            if (v.layoutManager == null) {
                v.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}
