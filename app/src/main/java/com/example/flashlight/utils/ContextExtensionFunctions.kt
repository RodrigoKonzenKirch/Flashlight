package com.example.flashlight.utils

import android.content.Context
import android.widget.Toast

fun Context.longToast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}
