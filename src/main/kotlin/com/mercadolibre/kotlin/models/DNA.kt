package com.mercadolibre.kotlin.models

data class DNA(val value: String) {
    fun matchWithPower(powers: List<Power>): Power? = powers.filter { value.contains(it.dnaIdentifier) }.getOrNull(0)
}