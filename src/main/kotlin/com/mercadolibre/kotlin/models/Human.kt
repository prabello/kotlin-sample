package com.mercadolibre.kotlin.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Human(val name: String, val genome: List<DNA>, val birth: LocalDate, @Id val id: String? = null){

    fun mutation(newGenome: List<DNA>): Human{
        return this.copy(genome = newGenome)
    }
}

data class DNA(val value: String){

}
fun String.toDna(): DNA = DNA(this)
