package com.mercadolibre.kotlin.models

import com.mercadolibre.kotlin.helpers.simpleHuman
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class HumanTest{
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
}