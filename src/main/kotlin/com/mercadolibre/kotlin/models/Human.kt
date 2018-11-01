package com.mercadolibre.kotlin.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Human(val name: String, val genome: List<DNA>, val birth: LocalDate,
                 @Id val id: String? = null) {

    val powers: List<Power>? = findPowersFor(genome)
    val powerLevel by lazy {
        powers?.map { it.battlePoints }?.fold(0L) { acc, i -> acc+i }?.toLong() ?: 0
    }
    val mutant: Boolean
        get() = powerLevel > 0

    fun sufferMutation(newGenome: List<DNA>): Human {
        return this.copy(genome = newGenome)
    }
}

private fun findPowersFor(genome: List<DNA>): List<Power>? {
    return genome.mapNotNull { dna -> dna.matchWithPower(Powers.getAllPowers()) }.ifEmpty { null }
}

fun String.toDna(): DNA = DNA(this)
