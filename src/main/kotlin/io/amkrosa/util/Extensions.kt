package io.amkrosa.util

fun String.capitalizeAndLowerCase(): String {
    val capitalized = this.substring(0,1)
    val rest = this.substring(1)
    return capitalized.plus(rest.lowercase())
}