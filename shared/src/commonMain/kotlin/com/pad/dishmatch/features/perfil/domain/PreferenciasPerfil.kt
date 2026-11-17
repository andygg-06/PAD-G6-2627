package com.pad.dishmatch.features.perfil.domain

/* PREFERENCIAS Y OPCIONES:
    - Adjetivos del perfil
    - Tipo de dieta
    - Alergias del perfil
    - Gustos gastronómicos: cocina italiana, japonesa, etc.
 */

// --- 1. Opciones relacionadas con el perfil ---
enum class UserAdj(val adj: String) {
    ALTO("Alto"),
    BAJO("Bajo"),
    ROMANTICO("Romántico"),
    DEPORTISTA("Deportista"),
    FIESTERO("Fiestero"),
}

enum class TipoDieta(val dieta: String) {
    NONE(""),
    VEGETARIANO("Vegetariano"),
    CELIACO("Celiaco"),
    VEGANO("Vegano"),
}

enum class Alergias(val adj: String) {
    // Normativa Europea de alergias
    GLUTEN("Gluten"),
    FRUTOS_CASCARA("Frutos de cáscara"),
    PESCADO("Pescado"),
    LACTOSA("Lactosa"),
    SOJA("Soja"),
    CRUSTACEOS("Crustáceos"),
    MOLUSCOS("Moluscos"),
    HUEVOS("Huevos"),
    CACAHUETES("Cacahuetes"),
    APIO("Apio"),
    MOSTAZA("Mostaza"),
    SESAMO("Sésamo"),
    ALTRAMUCES("Altramuces"),
    SULFITOS("Sulfitos"),
}

enum class AppTema(val tema: String) {
    LIGHT("Claro"),
    DARK("Oscuro"),
    SYSTEM_DEFAULT("Tema del sistema"),
}

// 2. Opciones relacionadas con el tipo de restaurante ---
enum class TipoCocinas(val cocina: String) {
    ITALIANO("Italiano"),
    INDIO("Indio"),
    ASIATICO("Asiático"),
}
enum class Presupuesto(val symbol: String) {
    ECONOMICO("$"),
    MEDIO("$$"),
    PREMIUM("$$"),
}

enum class TipoLocal(val adj: String) {
    RESTAURANTE("Restaurante"),
    BAR("Bar"),
    CAFE("Cafe"),
    THEME_PARK("Parque de atracciones"),
}