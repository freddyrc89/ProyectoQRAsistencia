package com.freddy.proyectoqrasistencia

data class Alumno(
    val dni: String,
    val nombre: String,
    val programa_estudios: String,
    val estado: String?,
    val observaciones: String?
)