package com.example.agroriego.data.model

data class TurnoRiego(
    val id: Int,
    val nombreParcela: String,
    val hectareas: Double,
    val tipoCultivo: String,
    val horaInicio: String,
    val estado: String = "Pendiente"
)