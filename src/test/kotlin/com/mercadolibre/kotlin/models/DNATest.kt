package com.mercadolibre.kotlin.models

import org.junit.jupiter.api.Test

internal class DNATest {

    @Test
    fun `Find powers that match the DNA`() {
        DNA("MONSTRO").matchWithPower(Powers.getAllPowers())
    }
}