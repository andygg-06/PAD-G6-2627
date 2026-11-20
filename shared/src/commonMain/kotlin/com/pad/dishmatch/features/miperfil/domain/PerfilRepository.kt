
package com.pad.dishmatch.features.miperfil.domain

import com.pad.dishmatch.model.Perfil

interface PerfilRepository {
    fun guardarPerfil(perfil: Perfil)
}