package com.example.sviat_minato.smsreply

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast
import android.os.Build
import android.os.Bundle
import android.util.Log


class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            intent.extras?.let {
                val pduArray = it.get("pdus") as Array<*>

                for (pduObject in pduArray) {
                    val currentMessage = getIncomingMessage(pduObject!!, it)
                    val phoneNumber = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    Log.i("SmsReceiver", "senderNum: $phoneNumber; message: $message")
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(context,"senderNum: $phoneNumber, message: $message", duration)
                    toast.show()
                }
            }
        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e)
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            SmsMessage.createFromPdu(aObject as ByteArray, bundle.getString("format"))
        else SmsMessage.createFromPdu(aObject as ByteArray)
    }
}