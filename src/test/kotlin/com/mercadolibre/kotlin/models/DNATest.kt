package com.mercadolibre.kotlin.models

import com.mercadolibre.kotlin.domains.DNA
import com.mercadolibre.kotlin.domains.Powers
import org.junit.jupiter.api.Test

internal class DNATest {

    @Test
    fun `Find powers that match the DNA`() {
        DNA("MONSTRO").matchWithPower(Powers.getAllPowers())
    }
}