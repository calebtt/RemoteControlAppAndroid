package com.example.remotecontrolnativeapp.network

import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketManager(
    private val onMessage: (String) -> Unit,
    private val onStatus: (Boolean) -> Unit
) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private var webSocket: WebSocket? = null

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                onStatus(true)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                onStatus(false)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                onStatus(false)
            }
        })
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Closing from client")
    }
}
