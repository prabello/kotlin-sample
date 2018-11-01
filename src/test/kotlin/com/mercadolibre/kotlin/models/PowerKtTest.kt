package com.mercadolibre.kotlin.models

import com.mercadolibre.kotlin.helpers.humanGenome
import com.mercadolibre.kotlin.helpers.mutantGenome
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PowerKtTest {

    @Test
    fun `When a mutant genome is given, find the powers for it`() {
        val powers = findPowers(mutantGenome())
        assertThat(powers).isNotEmpty
    }

    @Test
    fun `When a human genome is given, return no powers`(){
        val powers = findPowers(humanGenome())
        assertThat(powers).isNullOrEmpty()
    }
}