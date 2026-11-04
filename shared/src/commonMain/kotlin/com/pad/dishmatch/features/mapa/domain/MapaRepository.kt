package com.pad.dishmatch.features.mapa.domain

interface MapaRepository {
    fun obtenerPuntos(): List<PuntoMapa>
    fun filtrarPorDistancia(maxMetros: Int): List<PuntoMapa>
}