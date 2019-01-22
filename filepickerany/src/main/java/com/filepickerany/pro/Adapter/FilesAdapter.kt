package com.filepickerany.pro.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.filepickerany.pro.Model.FileDetails
import com.filepickerany.pro.R
import com.squareup.picasso.Picasso
import java.io.File

class FilesAdapter(callBack:ClickCallBack,files:ArrayList<FileDetails>): RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    private var fileList:ArrayList<FileDetails> = ArrayList()
    private var callBack:ClickCallBack = callBack

    init {
        fileList = files
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: FilesAdapter.ViewHolder, position: Int) {
        var file = fileList[position]
        holder.name.text = file.fileName
        holder.cbk.isChecked = file.isSelected

        val pathOfFile = File(file.filePath)
        //Log.e("inside Rv","${pathOfFile.absolutePath}")
        Log.e("ends with","${pathOfFile.endsWith(".jpeg")} && ${pathOfFile.endsWith(".png")}")
        if (pathOfFile.name.endsWith(".jpeg")){
            holder.fileType.text = ""
            Picasso.get().load(pathOfFile).placeholder(R.drawable.ic_image_grey).into(holder.image)
        }else if (pathOfFile.name.endsWith(".png")){
            holder.fileType.text = ""
            Picasso.get().load(pathOfFile).into(holder.image)
        }

        holder.cbk.setOnClickListener {
            if (callBack != null){
                callBack.onClick(position,file)
            }
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        lateinit var image:ImageView
        lateinit var name:TextView
        lateinit var cbk:CheckBox
        lateinit var fileType:TextView
        init {
            image = itemView.findViewById(R.id.image_view)
            name = itemView.findViewById(R.id.file_name)
            cbk = itemView.findViewById(R.id.file_cbk)
            fileType = itemView.findViewById(R.id.file_type_tv)
        }
    }

    interface ClickCallBack{
        fun onClick(position:Int,fileDetails: FileDetails)
    }
}