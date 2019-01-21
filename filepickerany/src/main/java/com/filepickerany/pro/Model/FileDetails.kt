package com.filepickerany.pro.Model

import android.net.Uri

data class FileDetails(
    var fileName: String,
    var filePath:String,
    var fileUri:Uri,
    var fileSize:Long,
    var isSelected:Boolean
)