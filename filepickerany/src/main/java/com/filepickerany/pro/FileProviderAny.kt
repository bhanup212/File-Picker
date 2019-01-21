package com.filepickerany.pro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

class FileProviderAny {

    private var fileList:ArrayList<String> = ArrayList()
    private var ctx:Context?=null
    private var MAX_FILES = 1


    companion object {
        const val TYPE_PDF = ".pdf"
        const val TYPE_JPEG = ".jpeg"
        const val TYPE_PNG = ".png"
        const val TYPE_DOC = ".doc"
        const val TYPE_ZIP = ".zip"
        const val TYPE_MP4 = ".mp4"
        const val TYPE_MP3 = ".mp3"
        const val TYPE_APK = ".apk"
        const val TYPE_ALL = "TYPE_ALL"

        val instance: FileProviderAny
            get() = FileProviderAny()
    }
    fun setMaxFiles(size:Int){
        MAX_FILES = size
    }

    fun pickAnyFile(context: Activity,REQUEST_CODE:Int){
        ctx = context

        val intent = Intent(ctx,FileActivity::class.java)
        intent.putExtra("MAX_FILE_SIZE",MAX_FILES)
        if (fileList.size == 0){
            intent.putExtra(TYPE_ALL, TYPE_ALL)
        }else{
            intent.putStringArrayListExtra("TYPE_LIST",fileList)
        }

        context.startActivityForResult(intent,REQUEST_CODE)
    }
    fun setFileTypes(fileType:ArrayList<String>){
        fileList = fileType
    }

    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}