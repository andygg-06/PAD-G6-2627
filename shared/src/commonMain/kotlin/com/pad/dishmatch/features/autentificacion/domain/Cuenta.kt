package com.pad.dishmatch.features.autentificacion.domain

import com.pad.dishmatch.model.Perfil
import kotlinx.serialization.Serializable

/*  --- DATA CLASS CUENTA ---
    Contiene los datos para poder iniciar sesión y crear la cuenta en caso de
    no existir.
    - La contraseña no la guardamos directamente, sino que la codificamos
    - Si la cuenta no existe, se registra el nuevo usuario y se crea el perfil
 */
@Serializable
data class Cuenta(
    val email: String,
    val sal: String,
    val hashPassword: String, // no guardamos la contraseña en claro
    val consentimientoSalud: Boolean, // hay que pedir consentimiento para guardar los datos de salud
    val perfil: Perfil,
)
/*  --- INTERFAZ PARA LOGIN ---
    Al iniciar sesión solo puede hacer dos cosas:
    - Registro exitoso
    - Fallo al registrarse
 */
sealed interface ResultadoAuth {
    data class Exito(val cuenta: Cuenta) : ResultadoAuth
    data class Fallo(val mensaje: String) : ResultadoAuth
}