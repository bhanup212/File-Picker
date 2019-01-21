package com.filepickerany.pro

import android.content.Context
import android.widget.Toast

class FileProvider {
    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}