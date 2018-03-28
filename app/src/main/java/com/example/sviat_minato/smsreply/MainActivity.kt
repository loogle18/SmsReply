package com.example.sviat_minato.smsreply

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.KeyEvent


class MainActivity : AppCompatActivity() {
    private val MY_REQUEST_READ_SMS = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNeededPermissions()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        println("KEY PRESSED")
        println("KEY: ${event.keyCode.toString()}")
        return super.dispatchKeyEvent(event)
    }

    private fun requestNeededPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            printSmsMessages()
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
            println("Permissions granted!")
            printSmsMessages()
        }
    }

    fun printSmsMessages() {
        val smsList = getAllSms("inbox")

        for (sms in smsList) {
            println("----------------------------------------")
            println("From ${sms.address}, message: ${sms.message}")
            println("----------------------------------------")
        }
    }

    fun getAllSms(folder: String): ArrayList<Sms> {
        val smsList = ArrayList<Sms>()
        val messages = Uri.parse("content://sms/$folder")
        val contentResolver = this.getContentResolver()
        val cursor = contentResolver.query(messages, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val sms = Sms(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                    address = cursor.getString(cursor.getColumnIndexOrThrow("address")),
                    message = cursor.getString(cursor.getColumnIndexOrThrow("body")),
                    readState = cursor.getString(cursor.getColumnIndex("read")),
                    time = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                    folder = folder
                )
                smsList.add(sms)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return smsList
    }
}
