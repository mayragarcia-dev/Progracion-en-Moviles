package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repository: AyudaRepository,
    private val preferencias: PreferenciasStore
) : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val modoOscuro: StateFlow<Boolean> = preferencias.modoOscuro.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        false
    )

    fun limpiarError() {
        _error.value = null
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol) {
        viewModelScope.launch {
            _error.value = null
            if (correo.isBlank() || clave.isBlank()) {
                _error.value = "Completa tu correo y contraseña."
                return@launch
            }

            val usuario = repository.login(correo.trim(), clave)
            if (usuario == null) {
                _error.value = "Correo o contraseña incorrectos"
                return@launch
            }
            if (usuario.rol != rolSeleccionado.name) {
                _error.value = "El usuario no corresponde al perfil seleccionado."
                return@launch
            }

            preferencias.guardarSesion(usuario.rol, usuario.id)
            _usuarioActual.value = usuario
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
        _error.value = null
    }
}
