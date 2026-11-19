package com.pad.dishmatch.model
enum class AdjPersonas(val adj: String) {
    FIESTERO("le gusta fiesta"),
    ALTO("alto"),
    BAJO("bajo"),
    ROMANTICO("romantico"),
    DEPORTISTA("Deportista")

}

enum class AdjRestaurante(val adj: String){
    ELEGANTE("elegante"),
    URBANO("urbano"),
    TRADICIONAL("tradicional"),
    RESTAURANTE("Restaurante"),
    BAR("Bar"),
    CAFE("Cafe"),
    THEME_PARK("Parque de atracciones"),
    ITALIANO("Italiano"),
    INDIO("Indio"),
    ASIATICO("Asiático")


}

enum class AdjPlato(val adj: String) {
    ALERGIA_PESCADO("Alergia al pescado"),
    ALERGIA_FRUTOS_SECOS("Alergia a frutos secos"),
    INTOLERANTE_LACTOSA("Intolerante a la lactosa"),
    PROTEINA("Alta en proteina"),
    VEGANO("Vegano"),
    VEGETARIANO("Vegetariano"),
    SALUDABLE("saludable"),
    CELIACO("Celiaco")

}