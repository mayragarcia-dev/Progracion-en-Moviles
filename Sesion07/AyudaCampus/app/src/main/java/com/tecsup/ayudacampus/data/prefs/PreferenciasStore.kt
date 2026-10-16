package com.tecsup.ayudacampus.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "ajustes")

class PreferenciasStore(private val context: Context) {
    val perfilActivo: Flow<String> = context.dataStore.data.map { preferencias ->
        preferencias[PERFIL_ACTIVO] ?: ""
    }

    val usuarioId: Flow<Int> = context.dataStore.data.map { preferencias ->
        preferencias[USUARIO_ID] ?: 0
    }

    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[MODO_OSCURO] ?: false
    }

    val notificaciones: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[NOTIFICACIONES] ?: true
    }

    val recibirTickets: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[RECIBIR_TICKETS] ?: true
    }

    suspend fun guardarSesion(rol: String, usuarioId: Int) {
        context.dataStore.edit { preferencias ->
            preferencias[PERFIL_ACTIVO] = rol
            preferencias[USUARIO_ID] = usuarioId
        }
    }

    suspend fun cambiarPerfil(rol: String, usuarioId: Int) = guardarSesion(rol, usuarioId)

    suspend fun guardarModoOscuro(activado: Boolean) {
        context.dataStore.edit { preferencias -> preferencias[MODO_OSCURO] = activado }
    }

    suspend fun guardarNotificaciones(activadas: Boolean) {
        context.dataStore.edit { preferencias -> preferencias[NOTIFICACIONES] = activadas }
    }

    suspend fun guardarRecibirTickets(activados: Boolean) {
        context.dataStore.edit { preferencias -> preferencias[RECIBIR_TICKETS] = activados }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { preferencias ->
            preferencias.remove(PERFIL_ACTIVO)
            preferencias.remove(USUARIO_ID)
        }
    }

    private companion object {
        val PERFIL_ACTIVO = stringPreferencesKey("perfilActivo")
        val USUARIO_ID = intPreferencesKey("usuarioId")
        val MODO_OSCURO = booleanPreferencesKey("modoOscuro")
        val NOTIFICACIONES = booleanPreferencesKey("notificaciones")
        val RECIBIR_TICKETS = booleanPreferencesKey("recibirTickets")
    }
}
