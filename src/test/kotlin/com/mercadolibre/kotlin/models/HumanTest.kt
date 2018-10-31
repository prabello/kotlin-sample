package com.mercadolibre.kotlin.models

import com.mercadolibre.kotlin.helpers.simpleHuman
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HumanTest{

    @Test
    fun `A normal human must have no power`(){
        //Given
        val simpleHuman = simpleHuman()
        //When
        assertThat(simpleHuman.powers)
                //Then
                .isNullOrEmpty()

    }

    @Test
    fun `A normal human is not a mutant`(){
        //Given
        val simpleHuman = simpleHuman()
        //When
        assertThat(simpleHuman.mutant)
                //Then
                .isFalse()
    }

    @Test
    fun `When a human suffer a mutation he may get powers`() {
        //Given
        val simpleHuman = simpleHuman()
        val monsterGenome = listOf("MONSTRO","HU3").map { DNA(it) }
        //When
        val monster = simpleHuman.sufferMutation(monsterGenome)
        //Then
        monster.powers?.apply{
            forEach { assertThat(it) is Power }
            assertThat(count()).isEqualTo(2)
        }
    }

    @Test
    fun `When a Human get powers he becomes a mutant`(){
        //Given
        val simpleHuman = simpleHuman()
        val monsterGenome = listOf("MONSTRO","HU3").map { DNA(it) }
        //When
        val monster = simpleHuman.sufferMutation(monsterGenome)
        //Then
        assertThat(monster.mutant).isTrue()
    }

    @Test
    fun `When a human becomes a mutant his power level increases`(){
        //Given
        val simpleHuman = simpleHuman()
        val monsterGenome = listOf("MONSTRO","HU3").map { DNA(it) }
        //When
        val monster = simpleHuman.sufferMutation(monsterGenome)
        //Then
        assertThat(monster.powerLevel).isGreaterThan(0)
    }
}