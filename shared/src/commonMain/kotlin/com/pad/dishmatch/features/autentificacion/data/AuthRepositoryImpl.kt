// features/auth/data/AuthRepositoryImpl.kt
package com.pad.dishmatch.features.autentificacion.data

import com.pad.dishmatch.features.autentificacion.domain.AuthRepository
import com.pad.dishmatch.features.autentificacion.domain.Cuenta
import com.pad.dishmatch.features.autentificacion.domain.ResultadoAuth
import com.pad.dishmatch.model.Perfil
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.kotlincrypto.hash.sha2.SHA256 // para poder pasar la contraseña a hash
import kotlin.random.Random


class AuthRepositoryImpl(private val settings: Settings = Settings()) : AuthRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private fun cuentas(): List<Cuenta> {
        val texto = settings.getStringOrNull(KEY_CUENTAS) ?: return emptyList()
        return try { json.decodeFromString(texto) } catch (e: Exception) { emptyList() }
    }

    private fun guardar(lista: List<Cuenta>) =
        settings.putString(KEY_CUENTAS, json.encodeToString(lista))

    private fun hash(password: String, sal: String): String =
        SHA256().digest((sal + password).encodeToByteArray()).toHexString()

    override fun registrar(
        email: String, password: String, perfil: Perfil, consentimientoSalud: Boolean
    ): ResultadoAuth {
        val correo = email.trim().lowercase()
        if ('@' !in correo || '.' !in correo.substringAfter('@'))
            return ResultadoAuth.Fallo("Correo no válido")
        if (password.length < 8)
            return ResultadoAuth.Fallo("La contraseña debe tener al menos 8 caracteres")
        if (cuentas().any { it.email == correo })
            return ResultadoAuth.Fallo("Ya existe una cuenta con ese correo")

        val sal = Random.nextBytes(16).toHexString()
        val cuenta = Cuenta(
            email = correo,
            sal = sal,
            hashPassword = hash(password, sal),
            consentimientoSalud = consentimientoSalud,
            // Sin consentimiento, no se guarda información de salud
            perfil = if (consentimientoSalud) perfil else perfil.copy(dieta = emptySet())
        )
        guardar(cuentas() + cuenta)
        settings.putString(KEY_SESION, correo)
        return ResultadoAuth.Exito(cuenta)
    }

    override fun iniciarSesion(email: String, password: String): ResultadoAuth {
        val correo = email.trim().lowercase()
        val cuenta = cuentas().firstOrNull { it.email == correo }
        // Mismo mensaje para "no existe" y "contraseña mala": no revela qué correos están registrados
        if (cuenta == null || cuenta.hashPassword != hash(password, cuenta.sal))
            return ResultadoAuth.Fallo("Correo o contraseña incorrectos")
        settings.putString(KEY_SESION, correo)
        return ResultadoAuth.Exito(cuenta)
    }

    override fun cuentaActual(): Cuenta? {
        val correo = settings.getStringOrNull(KEY_SESION) ?: return null
        return cuentas().firstOrNull { it.email == correo }
    }

    override fun actualizarPerfil(perfil: Perfil) {
        val actual = cuentaActual() ?: return
        guardar(cuentas().map { if (it.email == actual.email) it.copy(perfil = perfil) else it })
    }

    override fun cerrarSesion() = settings.remove(KEY_SESION)

    private companion object {
        const val KEY_CUENTAS = "cuentas"
        const val KEY_SESION = "sesion"
    }
}