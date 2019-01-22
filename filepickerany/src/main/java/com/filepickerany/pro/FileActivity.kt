package com.filepickerany.pro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filepickerany.pro.Adapter.FilesAdapter
import com.filepickerany.pro.Model.FileDetails
import org.jetbrains.anko.doAsync
import java.io.File

class FileActivity : AppCompatActivity(), FilesAdapter.ClickCallBack {
    override fun onClick( position:Int,fileDetails: FileDetails) {
        Log.e(TAG," file name is ${fileDetails.filePath}")

        if (selectedFiles.contains(fileDetails.filePath)){
            selectedFiles.remove(fileDetails.filePath)
            filteredFiles[position].isSelected = false
        }else{
            if (selectedFiles.size >= maxFiles){
                Toast.makeText(this,"max files selected",Toast.LENGTH_SHORT).show()
            }else {
                selectedFiles.add(fileDetails.filePath)
                filteredFiles[position].isSelected = true
            }
        }
        supportActionBar!!.title = "${selectedFiles.size} Files selected"
        fileAdapter.notifyDataSetChanged()
    }

    private lateinit var filesRv: RecyclerView
    private var fileTypes: ArrayList<String> = ArrayList()
    private var filteredFiles: ArrayList<FileDetails> = ArrayList()
    private lateinit var fileAdapter: FilesAdapter
    private var maxFiles = 1
    private var selectedFiles:ArrayList<String> = ArrayList()

    companion object {
        const val PERMISSION_REQUEST = 202
        const val TAG = "FileActivity"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        supportActionBar!!.title = "0 Files selected"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        maxFiles = intent.getIntExtra("MAX_FILE_SIZE", 1)
        val fileTye = intent.getStringArrayListExtra("TYPE_LIST")
        checkFileTypes(fileTye)
        init()
        fileAdapter = FilesAdapter(this, filteredFiles)
        filesRv.layoutManager = GridLayoutManager(this, 3)
        filesRv.itemAnimator = DefaultItemAnimator()
        filesRv.adapter = fileAdapter
        fileAdapter.notifyDataSetChanged()

    }

    private fun init() {
        filesRv = findViewById(R.id.file_rv)
    }

    private fun checkFileTypes(fileType: ArrayList<String>) {

        for (type in fileType) {
            Log.e(TAG,"file type is: $type")
            when (type) {
                FileProviderAny.TYPE_PDF -> {
                    fileTypes.add(FileProviderAny.TYPE_PDF)
                }
                FileProviderAny.TYPE_JPEG -> {
                    fileTypes.add(FileProviderAny.TYPE_JPEG)
                }
                FileProviderAny.TYPE_PNG -> {
                    fileTypes.add(FileProviderAny.TYPE_PNG)
                }
                FileProviderAny.TYPE_DOC -> {
                    fileTypes.add(FileProviderAny.TYPE_DOC)
                }
                FileProviderAny.TYPE_ZIP -> {
                    fileTypes.add(FileProviderAny.TYPE_ZIP)
                }
                FileProviderAny.TYPE_MP4 -> {
                    fileTypes.add(FileProviderAny.TYPE_MP4)
                }
                FileProviderAny.TYPE_MP3 -> {
                    fileTypes.add(FileProviderAny.TYPE_MP3)
                }
                FileProviderAny.TYPE_APK -> {
                    fileTypes.add(FileProviderAny.TYPE_APK)
                }

            }
        }

        if (isPermissionGranted()) {
            fetchFiles()
        } else {
            requestPermission()
        }
    }

    private fun isPermissionGranted(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@FileActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted.
                    fetchFiles()
                } else {
                    // User refused to grant permission. You can add AlertDialog here
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.file_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.ok_menu -> {
                val intent = Intent()
                intent.putExtra("data",selectedFiles)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchFiles(dir: File = Environment.getExternalStorageDirectory()) {

        doAsync {
            var allFolder = dir.listFiles()
            if (allFolder != null) {
                for (folder in allFolder) {
                    if (folder.isDirectory) {
                        fetchFiles(folder)
                    } else {
                        Log.d("File", "file name is: ${folder.name}")
                        if (folder.name.endsWith(".pdf") && fileTypes.contains(FileProviderAny.TYPE_PDF)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".png") && fileTypes.contains(FileProviderAny.TYPE_PNG)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".jpeg") && fileTypes.contains(FileProviderAny.TYPE_JPEG)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } /*else if (folder.name.endsWith(".doc") && fileTypes.contains(FileProviderAny.TYPE_DOC)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".zip") && fileTypes.contains(FileProviderAny.TYPE_ZIP)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".mp4") && fileTypes.contains(FileProviderAny.TYPE_MP4)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".mp3") && fileTypes.contains(FileProviderAny.TYPE_MP3)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".apk") && fileTypes.contains(FileProviderAny.TYPE_APK)) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        } else if (folder.name.endsWith(".png")) {

                            val file = FileDetails(
                                folder.name,
                                folder.absolutePath,
                                Uri.parse(folder.absolutePath),
                                folder.length(),
                                false
                            )
                            filteredFiles.add(file)
                        }*/
                        runOnUiThread {
                            fileAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    }
}
