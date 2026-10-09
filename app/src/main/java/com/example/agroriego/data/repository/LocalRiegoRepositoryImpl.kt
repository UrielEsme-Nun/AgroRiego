package com.example.agroriego.data.repository

import com.example.agroriego.data.model.TurnoRiego
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalRiegoRepositoryImpl : RiegoRepository {

    private val turnosIniciales = mutableListOf(
        TurnoRiego(1, "Parcela La Esperanza", 4.5, "Maíz", "07:30 AM", "Aprobado"),
        TurnoRiego(2, "Don Pedro Norte", 2.0, "Hortalizas", "05:00 PM", "Pendiente")
    )

    private val _turnosFlow = MutableStateFlow<List<TurnoRiego>>(turnosIniciales)

    override fun obtenerTurnos(): Flow<List<TurnoRiego>> = _turnosFlow.asStateFlow()

    override suspend fun agregarTurno(turno: TurnoRiego) {
        turnosIniciales.add(turno)
        _turnosFlow.value = turnosIniciales.toList()
    }
}