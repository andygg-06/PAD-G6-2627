package com.pad.dishmatch.model

import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjRestaurante

class Restaurante(
    val nom:String,
    val ubicacion:String,
    val precioMin: Int,
    val precioMax: Int,
    val estilosRestaurante: Array<AdjRestaurante> = emptyArray<AdjRestaurante>(),
    val Carta: Array<Plato> = emptyArray<Plato>()





)