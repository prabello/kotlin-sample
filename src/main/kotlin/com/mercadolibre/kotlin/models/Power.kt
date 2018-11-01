package com.mercadolibre.kotlin.models

enum class La(val battlePoints: Int, val dnaIdentifier: String) {
    SUPER_STRENGHT(300, "MONSTRO"),
    FLY(200, "BIRD"),
    LASER_BEANS(250, "PEWPEW"),
    POOP_ATTACK(9001, "HU3")
}

interface Power{
    val battlePoints: Long
    val dnaIdentifier: String
}

class SuperStrenght(override val battlePoints: Long = 300, override val dnaIdentifier: String = "MONSTRO") : Power
class Fly(override val battlePoints: Long = 200, override val dnaIdentifier: String = "BIRD") : Power
class LaserBeans(override val battlePoints: Long = 250, override val dnaIdentifier: String = "PEWPEW") :Power
class PoopAttack(override val battlePoints: Long = 9001, override val dnaIdentifier: String = "HU3") :Power

fun findPowers(genome: List<DNA>): List<Power>? {
    val powerFullList = Powers.getAllPowers()
    return powerFullList.filter { power ->
        genome.any { dna -> dna.value.contains(power.dnaIdentifier) }
    }
}

object Powers{
    fun getAllPowers() = listOf(SuperStrenght(), Fly(), LaserBeans(), PoopAttack())
}
