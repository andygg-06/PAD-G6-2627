package com.pad.dishmatch.features.profile.domain.model

import kotlinx.serialization.Serializable

// MODELO PRINCIPAL DEL USUARIO

// Contiene los datos principales del usuario
@Serializable
data class UserProfile(
    val id: String, // se asigna un id aleatorio único
    val email: String,
    val basicInfo: UserBasicInfo,
    val gastroPref: GastroPref = GastroPref(),
    val healthProfile: HealthProfile = HealthProfile(),
    val settings: userSettings = UserSettings(),
    val friendsIds: List<String> = emptyList() // grafo de amistades
)

// --- 1. Información básica del usuario ---
@Serializable
data class UserBasicInfo(
    var name: String,
    val birthDate: String, // tipo datetime
    val age: Int, // se calcula automáticamente (investigar)
    val photoURL: String? = null,
    val bio: String = "", // maximo 100 caracteres
    val location: String
)

// --- 2. Preferencias Gastronomicas ---
@Serializable
data class GastroPref(
    val budgetRange: BudgetRange = BudgetRange.ECONOMIC, // por defecto
    val favCuisines: List<String> = emptyList()
)

enum class BudgetRange(val symbol: String) {
    ECONOMIC("$"),
    MEDIUM("$$"),
    PREMIUM("$$")
}