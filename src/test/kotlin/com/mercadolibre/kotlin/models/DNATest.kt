package com.mercadolibre.kotlin.models

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DNATest {

    @Test
    fun `Find powers that match the DNA`() {
        DNA("MONSTRO").matchWithPower(Power.values())
    }
}