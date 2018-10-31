package com.mercadolibre.kotlin.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HumanKtTest {

    @Test
    fun `Must be able to transform a single string into a DNA sequence`() {
        val dna = "pepino".toDna()
        assertThat(dna).isInstanceOf(DNA::class.java)
    }
}