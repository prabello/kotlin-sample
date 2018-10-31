package com.mercadolibre.kotlin.helpers

import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.models.toDna
import java.time.LocalDate
import java.time.Month

fun simpleHuman(): Human {
    val birth = LocalDate.of(1969, Month.JANUARY, 3)
    val genome = listOf("ATGCGA".toDna(), "CAGTGC".toDna(), "TTATGT".toDna(), "AGAAGG".toDna(), "CCCCTA".toDna(), "TCACTG".toDna())
    return Human(name = "Schumacher", genome = genome, birth = birth)
}