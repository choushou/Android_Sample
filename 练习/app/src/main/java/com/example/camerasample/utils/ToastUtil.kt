package com.example.camerasample.utils

import android.content.Context
import android.widget.Toast
import java.time.Duration

class ToastUtil {
    companion object{
        fun showShortToast(context: Context,message:String){
            showToastMessage(context,message,Toast.LENGTH_SHORT)
        }

        fun showToastMessage(context: Context,message: String,duration: Int){
            Toast.makeText(context,message,duration).show()
        }
    }
}