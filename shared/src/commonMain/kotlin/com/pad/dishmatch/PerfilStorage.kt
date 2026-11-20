package com.pad.dishmatch


import com.pad.dishmatch.model.Perfil
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PerfilStorage {
    private val settings = Settings()

    fun cargar(): Perfil {
        val texto = settings.getStringOrNull("perfil") ?: return Perfil()
        return try {
            Json.decodeFromString<Perfil>(texto)
        } catch (e: Exception) {
            Perfil()
        }
    }

    fun guardar(perfil: Perfil) {
        settings.putString("perfil", Json.encodeToString(perfil))
    }
}