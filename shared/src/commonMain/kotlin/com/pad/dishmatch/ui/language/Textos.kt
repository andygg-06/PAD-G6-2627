package com.pad.dishmatch.ui.language

import androidx.compose.runtime.staticCompositionLocalOf

data class Textos(
    val volver: String,
    val conQuienComes: String,
    val conocerAlguien: String,
    val conocerDescripcion: String,
    val elegirRestaurante: String,
    val elegirRestauranteDescripcion: String,
    val accesosRapidos: String,
    val calendario: String,
    val mapa: String,
    val ajustes: String,
    val guardar: String,
    val cancelar: String,
)

val TextosEs = Textos(
    volver = "← Volver",
    conQuienComes = "¿Con quién comes hoy?",
    conocerAlguien = "Conocer a alguien",
    conocerDescripcion = "Personas con tus mismos gustos, cerca de ti",
    elegirRestaurante = "Elegir restaurante",
    elegirRestauranteDescripcion = "Sitios que encajan con tu carta de preferencias",
    accesosRapidos = "ACCESOS RÁPIDOS",
    calendario = "Calendario",
    mapa = "Mapa",
    ajustes = "Ajustes",
    guardar = "Guardar",
    cancelar = "Cancelar",
)

val TextosEn = Textos(
    volver = "← Back",
    conQuienComes = "Who are you eating with today?",
    conocerAlguien = "Meet someone",
    conocerDescripcion = "People with your tastes, near you",
    elegirRestaurante = "Choose a restaurant",
    elegirRestauranteDescripcion = "Places that match your preferences",
    accesosRapidos = "QUICK ACCESS",
    calendario = "Calendar",
    mapa = "Map",
    ajustes = "Settings",
    guardar = "Save",
    cancelar = "Cancel",
)

val LocalTextos = staticCompositionLocalOf { TextosEs }