package com.example.scaler.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

fun Activity.hideShowActionBar(show:Boolean) {
    if(this is AppCompatActivity){
        if(show) this.supportActionBar?.show() else this.supportActionBar?.hide()
    }
}