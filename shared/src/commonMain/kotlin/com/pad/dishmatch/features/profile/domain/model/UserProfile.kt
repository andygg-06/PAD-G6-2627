package com.pad.dishmatch.features.profile.domain.model

import kotlinx.serialization.Serializable

/* MODELO PRINCIPAL DEL USUARIO:
    - Estructura base
    - Información del usuario
    - Preferencias gastronómicas
    - Salud del usuario: dietas, alérgenos ...
    - Tipos enumerados para cada una de las opciones posibles
    - Ajustes de perfil
 */

// --- 0. Estructura base ---
@Serializable
data class UserProfile(
    val id: String, // se asigna un id aleatorio único
    val email: String,
    val basicInfo: UserBasicInfo,
    val gastroPref: GastroPref = GastroPref(),
    val healthProfile: HealthProfile = HealthProfile(),
    val settings: UserSettings = UserSettings(),
    val friendsIds: List<String> = emptyList() // grafo de amistades
)

// --- 1. Información básica del usuario ---
@Serializable
data class UserBasicInfo(
    var name: String,
    val birthDate: String, // tipo datetime
    val age: Int, // se calcula automáticamente (investigar)
    val photoURL: String? = null,
    val bio: String = "", // máximo 100 caracteres
    val location: String
)

// --- 2. Preferencias Gastronómicas ---
@Serializable
data class GastroPref(
    val budgetRange: BudgetRange = BudgetRange.ECONOMIC, // por defecto
    val localType: LocalType = LocalType.BAR,
    val favCuisines: List<String> = emptyList()
)

enum class BudgetRange(val symbol: String) {
    ECONOMIC("$"),
    MEDIUM("$$"),
    PREMIUM("$$")
}

enum class LocalType {
    RESTAURANT,
    BAR,
    CAFE,
    THEME_PARK
}

// --- 3. Perfil de Salud y Nutrición
@Serializable
data class HealthProfile(
    val diet: DietType = DietType.NONE,
    val allergens: List<String> = emptyList(),
    val foodToAvoid: List<String> = emptyList()
)

enum class DietType {
    NONE,
    VEGETARIAN,
    VEGAN
}

// --- 4. Configuración y Personalización ---
@Serializable
data class UserSettings(
    val theme: AppTheme = AppTheme.SYSTEM_DEFAULT,
    val language: String = "es" // Código ISO: "es", "en"
)

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM_DEFAULT
}