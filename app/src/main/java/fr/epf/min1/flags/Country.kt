package fr.epf.min1.flags

data class Country(
    val name: Name,
    val flags: Flags,
    val capital: List<String>,
    val population: Int,
    val region: String,
    val subregion: String
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val svg: String,
    val png: String
)