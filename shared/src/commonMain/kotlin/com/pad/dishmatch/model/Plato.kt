package com.pad.dishmatch.model

import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjRestaurante

class Plato(
    val nom:String,
    val precio: Int,

    val tipo: Array<AdjPlato> = emptyArray<AdjPlato>()



)