package com.mercadolibre.kotlin.models

import com.mercadolibre.kotlin.domains.Power
import com.mercadolibre.kotlin.domains.Powers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.reflections.Reflections


internal class PowersTest {

    @Test
    fun `Make sure allPowers return all powers`() {
        val powersFromReflection = Reflections("com.mercadolibre.kotlin").getSubTypesOf(Power::class.java)
        val powersFromMethod = Powers.getAllPowers().map { it::class.java }
        assertThat(powersFromReflection).containsExactlyInAnyOrderElementsOf(powersFromMethod)
    }
}
