package com.promptsoftech.logindemo.utils

import android.os.StrictMode
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class AppUtils {
    companion object{
        fun isOnline(): Boolean {
            return try {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                val timeoutMs = 1500
                val sock = Socket()
                val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
                sock.connect(sockaddr, timeoutMs)
                sock.close()
                true
            } catch (e: IOException) {
                false
            }
        }
    }
}