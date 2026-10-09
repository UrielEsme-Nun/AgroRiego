package com.example.agroriego.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroriego.data.model.TurnoRiego
import com.example.agroriego.data.repository.LocalRiegoRepositoryImpl
import com.example.agroriego.data.repository.RiegoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RiegoUiState(
    val listaTurnos: List<TurnoRiego> = emptyList(),
    val nombreParcelaInput: String = "",
    val hectareasInput: String = "",
    val cultivoSeleccionado: String = "Maíz",
    val horaInicioSeleccionada: String = "08:00 AM",
    val alertasClima: Boolean = true,
    val recordatorios: Boolean = true
)

class RiegoViewModel(
    private val repository: RiegoRepository = LocalRiegoRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RiegoUiState())
    val uiState: StateFlow<RiegoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.obtenerTurnos().collect { lista ->
                _uiState.value = _uiState.value.copy(listaTurnos = lista)
            }
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(nombreParcelaInput = nombre)
    }

    fun onHectareasChange(ha: String) {
        _uiState.value = _uiState.value.copy(hectareasInput = ha)
    }

    fun onCultivoSelected(cultivo: String) {
        _uiState.value = _uiState.value.copy(cultivoSeleccionado = cultivo)
    }

    fun onHoraSelected(hora: String) {
        _uiState.value = _uiState.value.copy(horaInicioSeleccionada = hora)
    }

    fun onAlertasToggle(checked: Boolean) {
        _uiState.value = _uiState.value.copy(alertasClima = checked)
    }

    fun onRecordatoriosToggle(checked: Boolean) {
        _uiState.value = _uiState.value.copy(recordatorios = checked)
    }

    fun guardarTurno() {
        val currentState = _uiState.value
        if (currentState.nombreParcelaInput.isNotBlank()) {
            val nuevoTurno = TurnoRiego(
                id = (System.currentTimeMillis() % 10000).toInt(),
                nombreParcela = currentState.nombreParcelaInput,
                hectareas = currentState.hectareasInput.toDoubleOrNull() ?: 1.0,
                tipoCultivo = currentState.cultivoSeleccionado,
                horaInicio = currentState.horaInicioSeleccionada
            )
            viewModelScope.launch {
                repository.agregarTurno(nuevoTurno)
                _uiState.value = _uiState.value.copy(
                    nombreParcelaInput = "",
                    hectareasInput = ""
                )
            }
        }
    }
}