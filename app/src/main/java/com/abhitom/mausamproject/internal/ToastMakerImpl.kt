package com.abhitom.mausamproject.internal

import android.content.Context
import android.widget.Toast

class ToastMakerImpl(context: Context) : ToastMaker {
    private val appContext = context.applicationContext
    override fun toastMaker(msg: String) {
        Toast.makeText(appContext,msg,Toast.LENGTH_LONG).show()
    }
}