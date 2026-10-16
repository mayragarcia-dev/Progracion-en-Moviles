package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoriasViewModel(private val repo: AyudaRepository) : ViewModel() {
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()
    val categorias: StateFlow<List<Categoria>> = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun alternar(categoria: Categoria) {
        viewModelScope.launch { repo.cambiarActiva(categoria.id, !categoria.activa) }
    }

    fun guardar(categoria: Categoria) {
        viewModelScope.launch {
            _mensaje.value = null
            runCatching { repo.guardarCategoria(categoria) }
                .onFailure { _mensaje.value = "No se pudo guardar la categoría." }
        }
    }

    fun eliminar(categoria: Categoria) {
        viewModelScope.launch {
            if (repo.contarTicketsCategoria(categoria.id) > 0) {
                _mensaje.value = "No se puede eliminar: la categoría ya tiene tickets."
            } else {
                runCatching { repo.eliminarCategoria(categoria) }
                    .onFailure { _mensaje.value = "No se pudo eliminar la categoría." }
            }
        }
    }
}
