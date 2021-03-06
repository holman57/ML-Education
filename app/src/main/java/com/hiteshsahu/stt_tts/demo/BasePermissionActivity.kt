package com.hiteshsahu.stt_tts.demo

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

abstract class BasePermissionActivity : AppCompatActivity() {

    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getActivityLayout())
        if (Build.VERSION.SDK_INT >= 23) {
            marshmallowBad()
        } else {
            setUpView()
        }
    }

    abstract fun setUpView()
    abstract fun getActivityLayout(): Int

    @TargetApi(Build.VERSION_CODES.M)
    private fun marshmallowBad() {
        val permissionsList = ArrayList<String>()
        if (!isPermissionGranted(permissionsList, Manifest.permission.RECORD_AUDIO))
            if (permissionsList.size > 0) {
                requestPermissions(permissionsList.toTypedArray(), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
                return
            }
        setUpView()
    }


    @TargetApi(Build.VERSION_CODES.M)
    private fun isPermissionGranted(permissionsList: MutableList<String>, permission: String): Boolean {

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            if (!shouldShowRequestPermissionRationale(permission))
                return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED
                for (i in permissions.indices)
                    perms[permissions[i]] = grantResults[i]
                if (perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED) {
                    setUpView()
                } else {
                    Toast.makeText(applicationContext, "Some Permissions are Denied Exiting App", Toast.LENGTH_SHORT)
                            .show()
                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}

