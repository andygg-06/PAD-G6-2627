

class Perfil(
    val nom:String,
    val edad:Int,
    val dieta: Array<AdjPlato> = emptyArray<AdjPlato>(),
    val personalidad: Array<AdjPersonas> = emptyArray<AdjPersonas>(),
    val estilosRestaurante: Array<AdjRestaurante> = emptyArray<AdjRestaurante>()

        )