package com.example.sviat_minato.smsreply

data class Sms(
        var id: String,
        var address: String,
        var message: String,
        var readState: String,
        var time: String,
        var folder: String
)