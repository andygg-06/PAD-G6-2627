
package com.pad.dishmatch.features.miperfil.data

import com.pad.dishmatch.features.autentificacion.data.AuthRepositoryImpl
import com.pad.dishmatch.features.miperfil.domain.PerfilRepository
import com.pad.dishmatch.model.Perfil

class PerfilRepositoryImpl(private val auth: AuthRepositoryImpl) : PerfilRepository {
    override fun guardarPerfil(perfil: Perfil) {
        auth.actualizarPerfil(perfil)
    }
}