package com.pad.dishmatch.model

class Restaurante(
    val nom:String,
    val ubicacion:String,
    val precioMin: Int,
    val precioMax: Int,
    val estilosRestaurante: Array<AdjRestaurante> = emptyArray<AdjRestaurante>(),
    val Carta: Array<Plato> = emptyArray<Plato>()





)