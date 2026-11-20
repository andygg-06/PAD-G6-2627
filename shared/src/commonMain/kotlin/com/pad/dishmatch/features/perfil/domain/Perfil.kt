package com.pad.dishmatch.features.perfil.domain

import kotlinx.serialization.Serializable

/* MODELO PRINCIPAL DEL USUARIO:
    - Estructura base
    - Información del usuario
    - Preferencias gastronómicas
    - Salud del usuario: dietas, alérgenos ...
    - Ajustes de perfil
 */

// --- 0. Estructura base ---
@Serializable
data class Perfil (
    val id: String, // se asigna un id aleatorio único
    val email: String,
    val basicInfo: BasicInfoPerfil,
    val gastroPref: GastroPref = GastroPref(),
    val perfilSalud: PerfilSalud = PerfilSalud(),
    val ajustes: Ajustes = Ajustes(),
    val amigosIds: List<String> = emptyList() // grafo de amistades
)

// --- 1. Información básica del usuario ---
@Serializable
data class BasicInfoPerfil(
    var nombre: String,
    val fechaNac: String, // tipo datetime
    val edad: Int, // se calcula automáticamente (investigar)
    val fotoURL: String? = null,
    val bio: String = "", // máximo 100 caracteres
    val adjPefil: Set<UserAdj> = emptySet<UserAdj>(),
    val localizacion: String
)

// --- 2. Preferencias Gastronómicas ---
@Serializable
data class GastroPref(
    val presupuesto: Presupuesto = Presupuesto.ECONOMICO, // por defecto
    val tipoLocal: TipoLocal = TipoLocal.BAR,
    val favCocinas: Set<TipoCocinas> = emptySet<TipoCocinas>()
)

// --- 3. Perfil de Salud y Nutrición
@Serializable
data class PerfilSalud(
    val dieta: TipoDieta = TipoDieta.NONE,
    val alergias: Set<Alergias> = emptySet<Alergias>()
)

// --- 4. Configuración y Personalización ---
@Serializable
data class Ajustes(
    val theme: AppTema = AppTema.SYSTEM_DEFAULT,
    val language: String = "es" // Código ISO: "es", "en"
)