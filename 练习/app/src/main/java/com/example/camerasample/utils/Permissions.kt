package com.example.camerasample.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class Permissions {
     companion object{
         fun showPermissionSettingDialog(context : Context, permissions:String){
             var msg:String = "本App需要"+permissions+"权限才能正常运行，请点击确定，进入设置界面进行授权处理~"
             var builder = AlertDialog.Builder(context)
             builder.setMessage(msg).setPositiveButton("确定") {
                 dialog, which -> showSettings(context)

             }.setNegativeButton("取消"){dialog, which ->  }.show()
         }

         fun showSettings(context: Context){
             var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
             var uri: Uri = Uri.fromParts("package",context.packageName, null)
             intent.data = uri
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
             context.startActivity(intent)
         }
     }
}