package com.catalin.test.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.catalin.library.FingerprintUtils

import com.catalin.test.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.finger_print_fragment.*
import javax.crypto.Cipher

class FingerPrintFragment : Fragment(), FingerprintUtils.Callback {
    override fun onSuccess(result: Cipher?) {
       Toast.makeText(activity, "Auth successful", Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    companion object {
        fun newInstance() = FingerPrintFragment()
    }

    private lateinit var viewModel: FingerPrintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.finger_print_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FingerPrintViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth_button.setOnClickListener {
            val biometricInfo = FingerprintUtils.BiometricInfo(getString(R.string.title_biometric_dialog), getString(R.string.subtitle_biometric_dialog), getString(R.string.label_biometric_negative_button))
            FingerprintUtils.showBiometricAuthenticationDialog(activity = activity as AppCompatActivity, info = biometricInfo, callback = this)
        }
    }

    override fun onError(errorString: String) {
        Toast.makeText(activity, errorString, Toast.LENGTH_LONG).show()
    }

}
