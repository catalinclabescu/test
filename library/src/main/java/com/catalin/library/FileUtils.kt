package com.catalin.library

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.*

object FileUtils {
    private val fileThread = Schedulers.newThread()

    @SuppressLint("CheckResult")
    fun <M : Parcelable> saveToFile(
        context: Context,
        name: String,
        data: M,
        callback: (file: File) -> Unit
    ) {
        Single.just(data).observeOn(fileThread).subscribeOn(AndroidSchedulers.mainThread())
            .map {
                saveToFile(context, name, data)
            }.subscribe(Consumer {
                callback(it)
            })
    }

    private fun <M : Parcelable> saveToFile(context: Context, name: String, data: M): File {
        val jsonString = Gson().toJson(data)
        val file = File(context.filesDir, name)
        if (!file.exists()) file.createNewFile()
        val bufferedWriter = BufferedWriter(FileWriter(file, true))
        bufferedWriter.write(EncryptionUtils.encrypt(jsonString))
        bufferedWriter.close()
        return file
    }

    @SuppressLint("CheckResult")
    fun <M> readFile(
        context: Context,
        name: String,
        objectClass: Class<M>,
        callback: (data: M?) -> Unit
    ) {
        Single.just(name).subscribeOn(fileThread).subscribeOn(AndroidSchedulers.mainThread())
            .map {
                readFile(context, name, objectClass)
            }.subscribe(Consumer {
                callback(it)
            })
    }

    fun <M> readFile(context: Context, name: String, objectClass: Class<M>): M? {
        val file = File(context.filesDir, name)
        if (!file.exists()) return null
        val bufferedReader = BufferedReader(FileReader(file))
        val stringBuilder = StringBuilder()
        for (line in bufferedReader.readLines()) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        return Gson().fromJson(stringBuilder.toString(), objectClass)
    }
}