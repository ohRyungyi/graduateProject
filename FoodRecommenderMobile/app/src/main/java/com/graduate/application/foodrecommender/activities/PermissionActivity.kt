package com.graduate.application.foodrecommender.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.graduate.application.foodrecommender.R

class PermissionActivity : AppCompatActivity() {

    private val permissions : Array<String> = arrayOf(
        Manifest.permission.CAMERA ,
        Manifest.permission.READ_EXTERNAL_STORAGE ,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val MULTI_PERMISSIONS : Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        if ( checkPermissions() ) {
            Log.d( "PERMISSION" , "all permission is granted")
            var intent = Intent( this , LoginActivity::class.java )
            startActivity( intent )
        }
        else {
            Log.d( "PERMISSION" , "all permissions is not granted")
            Toast.makeText( this , "모든 권한이 허용되지 않았습니다. 설정창을 통해 모든 권한을 허용해주세요" , Toast.LENGTH_SHORT ).show()
            finish()
            checkPermissions()
        }
    }

    fun checkPermissions() : Boolean {
        var result : Int = 0
        var permissionList : ArrayList<String> = ArrayList()

        for ( permission in permissions ) {
            result = ActivityCompat.checkSelfPermission( this , permission )
            if ( result == PackageManager.PERMISSION_DENIED ) {
                permissionList.add( permission )
            }
        }

        if ( !permissionList.isEmpty() ) {

            var permissions : Array<String?> = arrayOfNulls<String>( permissionList.size )
            var ind : Int = 0
            for ( permission in permissionList ) {
                permissions.set( ind , permission )
                ind += 1
            }

            ActivityCompat.requestPermissions( this ,
                permissions , MULTI_PERMISSIONS )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ( requestCode == MULTI_PERMISSIONS ) {
            for ( result in grantResults ) {
                if ( result == PackageManager.PERMISSION_DENIED ) {
                    showToast_PermissionDeny()
                }
            }

            if ( grantResults.size <= 0 ) {
                showToast_PermissionDeny()
            }
        }

        return
    }

    fun showToast_PermissionDeny()  {
        Toast.makeText( this , "권한 요청에 동의를 해야 합니다. 설정에서 권한 허용을 해주세요" , Toast.LENGTH_SHORT ).show()
        finish()
    }
}