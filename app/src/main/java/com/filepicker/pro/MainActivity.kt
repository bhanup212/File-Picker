package com.filepicker.pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.filepickerany.pro.FileProviderAny

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FileProviderAny.instance.setMaxFiles(3).setFileTypes(arrayListOf(".pdf",".png")).pickAnyFile(this,222)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            222 ->{
                if (data != null) {
                    val list: ArrayList<String> = data!!.extras.get("data") as ArrayList<String>
                    for (path in list) {
                        Log.e("path", "path is: $path")
                    }
                }
            }
        }
    }
}
