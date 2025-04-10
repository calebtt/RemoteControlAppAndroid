package com.example.remotecontrolnativeapp.state

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotecontrolnativeapp.network.WebSocketManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.util.*

private val Context.dataStore by preferencesDataStore(name = "settings")

class ControlViewModel(app: Application) : AndroidViewModel(app) {
    private val SESSION_KEY = stringPreferencesKey("session_token")
    private val UUID_KEY = stringPreferencesKey("web_client_uuid")
    private val context = app.applicationContext

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val _logs = MutableStateFlow<List<String>>(emptyList())
    val logs: StateFlow<List<String>> = _logs

    var sessionToken: String = ""
        set(value) {
            field = value
            saveToken(value)
        }

    var clientId: String = ""
        private set

    private val wsManager = WebSocketManager(
        onMessage = { appendLog("[←] $it") },
        onStatus = { connected ->
            _isConnected.value = connected
            appendLog(if (connected) "✅ Connected" else "❌ Disconnected")
        }
    )

    init {
        viewModelScope.launch {
            clientId = loadUUID().ifBlank {
                UUID.randomUUID().toString().also { saveUUID(it) }
            }
            val saved = loadToken()
            sessionToken = saved
            if (saved.isNotBlank()) {
                connect()
            }
        }
    }

    fun connect() {
        appendLog("🌐 Connecting...")
        wsManager.connect("wss://arcserver.cloud/ws/")

        viewModelScope.launch {
            delay(500) // wait for connection
            sendHandshake()
        }
    }

    private fun sendHandshake() {
        if (sessionToken.isBlank()) return

        val handshake = JSONObject().apply {
            put("session_token", sessionToken)
            put("client_type", "web")
            put("client_id", clientId)
        }

        wsManager.send(handshake.toString())
        appendLog("[→] Handshake $handshake")
    }

    fun sendCommand(action: String, state: String) {
        if (sessionToken.isBlank()) return

        val message = JSONObject().apply {
            put("session_token", sessionToken)
            put("client_id", clientId)
            put("command", action)
            put("state", state)
        }
        wsManager.send(message.toString())
        appendLog("[→] $message")
    }

    override fun onCleared() {
        super.onCleared()
        wsManager.close()
    }

    private fun saveToken(token: String) {
        viewModelScope.launch {
            context.dataStore.edit { it[SESSION_KEY] = token }
        }
    }

    private suspend fun loadToken(): String {
        return context.dataStore.data.map { prefs -> prefs[SESSION_KEY] ?: "" }.first()
    }

    private fun appendLog(entry: String) {
        _logs.update { it.takeLast(50) + entry }
    }

    private suspend fun loadUUID(): String {
        return context.dataStore.data.map { prefs -> prefs[UUID_KEY] ?: "" }.first()
    }

    private fun saveUUID(uuid: String) {
        viewModelScope.launch {
            context.dataStore.edit { it[UUID_KEY] = uuid }
        }
    }
}
