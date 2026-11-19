package com.pad.dishmatch.features.autentificacion.domain

import com.pad.dishmatch.model.Perfil

/*  --- INTERFAZ LOGIN ---
    Interfaz para las funciones de inicio de sesión:
    - Registro del nuevo perfil si no existe
    - Inicio de sesión si existe la cuenta
    - Cerrar sesión de la cuenta
 */

interface AuthRepository {
    fun registrar(email: String, password: String, perfil: Perfil, consentimientoSalud: Boolean): ResultadoAuth
    fun iniciarSesion(email: String, password: String): ResultadoAuth
    fun cuentaActual(): Cuenta?
    fun actualizarPerfil(perfil: Perfil)
    fun cerrarSesion()
}