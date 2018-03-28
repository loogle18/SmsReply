package com.example.sviat_minato.smsreply


import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log


class MainActivity : AppCompatActivity() {
    private val REQUEST_ALl_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionsIfNeeded()
    }

    private fun requestPermissionsIfNeeded() {
        if (checkingPermission()) {
            Log.i("RequestPermissions", "Permissions granted!")
        } else {
            requestNeededPermissions()
        }
    }

    private fun requestNeededPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_SMS, SEND_SMS, RECEIVE_SMS, READ_CONTACTS),
                REQUEST_ALl_CODE
        )
    }

    private fun checkingPermission(): Boolean {
        val readSms = ContextCompat.checkSelfPermission(applicationContext, READ_SMS)
        val sendSms = ContextCompat.checkSelfPermission(applicationContext, SEND_SMS)
        val receiveSms = ContextCompat.checkSelfPermission(applicationContext, RECEIVE_SMS)
        val readContacts = ContextCompat.checkSelfPermission(applicationContext, READ_CONTACTS)

        return readSms == PackageManager.PERMISSION_GRANTED &&
                sendSms == PackageManager.PERMISSION_GRANTED &&
                receiveSms == PackageManager.PERMISSION_GRANTED &&
                readContacts == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_ALl_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val readSms = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val sendSms = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    val receiveSms = grantResults[2] == PackageManager.PERMISSION_GRANTED
                    val readContacts = grantResults[3] == PackageManager.PERMISSION_GRANTED

                    if (readSms && sendSms && receiveSms && readContacts) {
                        Log.i("PermissionsResult", "Permission Granted!")
                    } else {
                        Log.i("PermissionsResult", "Permission Denied!")
                    }
                }
            }
        }
    }
}
