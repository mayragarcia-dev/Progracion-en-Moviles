package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriasViewModel(private val repo: AyudaRepository) : ViewModel() {
    val categorias: StateFlow<List<Categoria>> = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun alternar(categoria: Categoria) {
        viewModelScope.launch { repo.cambiarActiva(categoria.id, !categoria.activa) }
    }

    fun guardar(categoria: Categoria) {
        viewModelScope.launch { repo.guardarCategoria(categoria) }
    }

    fun eliminar(categoria: Categoria) {
        viewModelScope.launch { repo.eliminarCategoria(categoria) }
    }
}
