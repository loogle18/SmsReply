package com.example.sviat_minato.smsreply

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log


class MainActivity : AppCompatActivity() {
    private val MY_REQUEST_READ_SMS = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNeededPermissions()
    }

    private fun requestNeededPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i("RequestPermissions", "Permissions granted!")
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_SMS),
                    MY_REQUEST_READ_SMS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == MY_REQUEST_READ_SMS && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("PermissionsResult", "Permissions granted!")
        }
    }
}
