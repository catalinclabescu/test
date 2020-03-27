package com.catalin.library


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import javax.crypto.Cipher


object FingerprintUtils {

    @StringRes
    private var defaultErrorMessage = R.string.message_auth_error_default

    fun showBiometricAuthenticationDialog(
        activity: AppCompatActivity,
        info: BiometricInfo,
        callback: Callback
    ) {

        if (BiometricManager.from(activity).canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
            callback.onError(activity.getString(defaultErrorMessage))
            return
        }

        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt =
            BiometricPrompt(activity, executor, @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    callback.onSuccess(result.cryptoObject?.cipher)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    callback.onError(errorString = errString.toString())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    callback.onError(activity.getString(defaultErrorMessage))
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle(info.title)
            .setSubtitle(info.subtitle).setNegativeButtonText(info.negativeButtonText).build()
        biometricPrompt.authenticate(promptInfo)


    }


    class BiometricInfo(val title: String, val subtitle: String, val negativeButtonText: String)
    interface Callback {
        fun onSuccess(result: Cipher?)
        fun onError(errorString: String)
    }
}
