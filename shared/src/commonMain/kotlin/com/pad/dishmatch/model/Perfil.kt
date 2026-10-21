import kotlinx.serialization.Serializable


@Serializable
data class Perfil(
    val nombre: String = "",
    val biografia: String = "",
    val foto: String? = null,
    val dieta: Array<AdjPlato> = emptyArray<AdjPlato>(),
    val personalidad: Array<AdjPersonas> = emptyArray<AdjPersonas>(),
    val busco: Array<AdjPersonas> = emptyArray<AdjPersonas>(),
    val estilosRestaurante: Array<AdjRestaurante> = emptyArray<AdjRestaurante>()
)