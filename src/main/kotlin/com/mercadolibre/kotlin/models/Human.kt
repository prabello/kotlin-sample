package com.mercadolibre.kotlin.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Human(val name: String, val genome: List<DNA>, val birth: LocalDate,
                 @Id val id: String? = null, val powers: List<Power>? = null) {

    fun sufferMutation(newGenome: List<DNA>): Human {
        return this.copy(genome = newGenome)
    }
}

enum class Power(val battlePoints: Int, val dnaIdentifier: String) {
    SUPER_STRENGHT(300, "MONSTRO"),
    FLY(200, "BIRD"),
    LASER_BEANS(250, "PEWPEW"),
    POOP_ATTACK(9001, "HU3")
}

data class DNA(val value: String)


fun String.toDna(): DNA = DNA(this)
