package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.domain.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NuevoTicketUiState(
    val categoriaSeleccionada: Categoria? = null,
    val titulo: String = "",
    val ubicacion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val descripcion: String = "",
    val errorCategoria: String? = null,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorEnvio: String? = null,
    val enviando: Boolean = false,
    val ticketCreadoId: Long? = null
)

class NuevoTicketViewModel(
    private val usuario: Usuario,
    private val repository: AyudaRepository
) : ViewModel() {
    val categorias: StateFlow<List<Categoria>> = repository.observarCategoriasActivas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(NuevoTicketUiState())
    val uiState: StateFlow<NuevoTicketUiState> = _uiState.asStateFlow()

    fun seleccionarCategoria(categoria: Categoria) {
        _uiState.value = _uiState.value.copy(categoriaSeleccionada = categoria, errorCategoria = null, errorEnvio = null)
    }

    fun cambiarTitulo(titulo: String) {
        _uiState.value = _uiState.value.copy(
            titulo = titulo,
            errorTitulo = if (titulo.trim().length >= 5) null else _uiState.value.errorTitulo,
            errorEnvio = null
        )
    }

    fun cambiarUbicacion(ubicacion: String) {
        _uiState.value = _uiState.value.copy(ubicacion = ubicacion, errorEnvio = null)
    }

    fun seleccionarPrioridad(prioridad: Prioridad) {
        _uiState.value = _uiState.value.copy(prioridad = prioridad, errorEnvio = null)
    }

    fun cambiarDescripcion(descripcion: String) {
        _uiState.value = _uiState.value.copy(
            descripcion = descripcion,
            errorDescripcion = if (descripcion.trim().length >= 10) null else _uiState.value.errorDescripcion,
            errorEnvio = null
        )
    }

    fun enviar() {
        val actual = _uiState.value
        if (actual.enviando || actual.ticketCreadoId != null) return
        val errorTitulo = if (actual.titulo.trim().length < 5) "Escribe al menos 5 caracteres." else null
        val errorDescripcion = if (actual.descripcion.trim().length < 10) "Describe el problema con al menos 10 caracteres." else null
        val categoria = actual.categoriaSeleccionada?.takeIf { elegida ->
            categorias.value.any { it.id == elegida.id && it.activa }
        }
        val errorCategoria = if (categoria == null) "Selecciona una categoría activa." else null
        if (errorTitulo != null || errorDescripcion != null || errorCategoria != null) {
            _uiState.value = actual.copy(
                errorTitulo = errorTitulo,
                errorDescripcion = errorDescripcion,
                errorCategoria = errorCategoria
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(enviando = true)
            runCatching {
                repository.crearTicket(
                    clienteId = usuario.id,
                    categoriaId = requireNotNull(categoria).id,
                    titulo = actual.titulo.trim(),
                    descripcion = actual.descripcion.trim(),
                    ubicacion = actual.ubicacion.trim(),
                    prioridad = actual.prioridad
                )
            }.onSuccess { id ->
                _uiState.value = _uiState.value.copy(enviando = false, ticketCreadoId = id)
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    enviando = false,
                    errorEnvio = "No se pudo crear el ticket. Inténtalo nuevamente."
                )
            }
        }
    }
}

class NuevoTicketViewModelFactory(
    private val usuario: Usuario,
    private val repository: AyudaRepository
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoTicketViewModel::class.java)) {
            return NuevoTicketViewModel(usuario, repository) as T
        }
        throw IllegalArgumentException("ViewModel no registrado: ${modelClass.name}")
    }
}
