package com.mercadolibre.kotlin.helpers

import com.mercadolibre.kotlin.domains.*
import java.time.LocalDate
import java.time.Month

fun simpleHuman(): Human {
    val birth = LocalDate.of(1969, Month.JANUARY, 3)
    return Human(name = "Schumacher", genome = humanGenome(), birth = birth)
}

fun humanGenome(): List<DNA> {
    return listOf("ATGCGA".toDna(), "CAGTGC".toDna(), "TTATGT".toDna(), "AGAAGG".toDna(), "CCCCTA".toDna(), "TCACTG".toDna())
}

fun mutantGenome(powersToAdd: List<Power>?): List<DNA> {
    return powersToAdd?.map { power -> DNA(power.dnaIdentifier) } ?: Powers.getAllPowers().map { DNA(it.dnaIdentifier) }
}

fun mutantGenome():List<DNA> = Powers.getAllPowers().map { DNA(it.dnaIdentifier) }
