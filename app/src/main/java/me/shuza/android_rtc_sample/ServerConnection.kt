package me.shuza.android_rtc_sample

import com.orhanobut.logger.Logger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.properties.Delegates

/**
 *
 * :=  created by:  Shuza
 * :=  create date:  2/3/2018
 * :=  (C) CopyRight Shuza
 * :=  www.shuza.me
 * :=  shuza.sa@gmail.com
 * :=  Fun  :  Coffee  :  Code
 *
 **/

class ServerConnection private constructor() {
    val BASE_URL = "ws://192.168.0.104:9090"
    var client: WebSocketClient by Delegates.notNull()
    var listener: ServerMessageHandler? = null

    init {
        try {
            val url = URI(BASE_URL)
            client = object : WebSocketClient(url) {
                override fun onOpen(handshakedata: ServerHandshake?) {
                    Logger.d("server connection opened")
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Logger.d("server connection closed")
                }

                override fun onMessage(message: String) {
                    if (listener == null) return
                    listener!!.onMessageReceived(message)
                }

                override fun onError(ex: java.lang.Exception) {
                    if (listener == null) return
                    listener!!.onMessageError(ex)
                }
            }
        } catch (e: Exception) {
            Logger.e("Server connection failed  ::  ${e.message}")
        }
    }

    companion object {
        var connectionHandler: ServerConnection? = null
        fun getInstance(): ServerConnection {
            if (connectionHandler == null) {
                connectionHandler = ServerConnection()
            }
            return connectionHandler!!
        }
    }

    fun send(message: String) {
        Logger.d(message)
        client.send(message)
    }

    interface ServerMessageHandler {
        fun onMessageReceived(message: String)
        fun onMessageError(e: Exception)
    }

}