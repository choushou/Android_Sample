package com.example.camerasample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.camerasample.utils.Permissions
import com.example.camerasample.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var cameraSavePath: File?=null
    private var uri: Uri?=null

    private var needPermissions : Array<String> = arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()

    }

    override fun onClick(v: View?) {
        TODO("not implemeted") //To change body of created functions use File | Settings | File Templates.

        when(v?.id){
            R.id.btn_system_camera -> goSystemCamera()
            R.id.btn_system_photo -> goSystemPhoto()
        }
    }

    fun checkNeedPermissions(){
        when(Build.VERSION.SDK_INT >= 23) {
            true -> when(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(
               this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
               this, Manifest.permission.RECORD_AUDIO
            ) !== PackageManager.PERMISSION_GRANTED
            ) {
                true -> ActivityCompat.requestPermissions(this,needPermissions,1)
            }
        }
    }

    fun initListener(){
        btn_system_camera.setOnClickListener(this)
        btn_system_photo.setOnClickListener(this)
    }

    fun goSystemCamera(){
         cameraSavePath = File(Environment.getExternalStorageDirectory().path + "/" + System.currentTimeMillis() + "jpg")
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           //uri=FileProvider.getUriForFile(this,SystemUtil.getPackageName())
        }

    }

    fun goSystemPhoto(){

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            1 -> when(grantResults.size > 1) {
                true -> when(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    true -> when(grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        true -> when(grantResults[2] == PackageManager.PERMISSION_GRANTED){

                        }
                        false -> Permissions.showPermissionSettingDialog(this,needPermissions[1])
                    }
                    false -> Permissions.showPermissionSettingDialog(this,needPermissions[0])
                }
                false -> ToastUtil.showShortToast(this,"请重新尝试")
            }
        }
    }

}
