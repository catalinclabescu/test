package com.catalin.test.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.catalin.library.MessagesCall

import com.catalin.test.R
import com.catalin.test.databinding.ApiListFragmentBinding

class ApiListFragment : Fragment() {
    private lateinit var binding: ApiListFragmentBinding
    companion object {
        fun newInstance() = ApiListFragment()
    }

    private lateinit var viewModel: ApiListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.api_list_fragment, container, false)
        return (binding as ViewDataBinding).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApiListViewModel::class.java)
        binding.viewModel = viewModel
        if (savedInstanceState == null) {
            viewModel.loading.set(true)
            MessagesCall().getMessages { 
                viewModel.updateMessages(messages = it)
            }
        }
    }

}
