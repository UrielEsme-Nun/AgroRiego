package com.example.agroriego.data.repository

import com.example.agroriego.data.model.TurnoRiego
import kotlinx.coroutines.flow.Flow

interface RiegoRepository {
    fun obtenerTurnos(): Flow<List<TurnoRiego>>
    suspend fun agregarTurno(turno: TurnoRiego)
}