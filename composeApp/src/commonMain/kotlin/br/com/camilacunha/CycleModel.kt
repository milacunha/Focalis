package br.com.camilacunha

data class CycleModel(
    val activeTime: String = "25",
    val restTime: String = "5",
    val cycles: String = "2",
    val date: String,
    val type: TypesOfAction
)

enum class TypesOfAction(val type: String) {
    DEFAULT("Tipo"),
    WORK("Trabalho"),
    HOBBIE("Lazer"),
    STUDY("Estudo"),
    CLEANING("Faxina")
}
