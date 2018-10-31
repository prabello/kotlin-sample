package com.mercadolibre.kotlin.handlers

import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import com.mercadolibre.kotlin.routers.HumanRouter
import com.mercadolibre.kotlin.validHuman
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@RunWith(SpringRunner::class)
@WebFluxTest
@Import(HumanRouter::class)
internal class HumanHandlerTesst {

    @Autowired
    private lateinit var makeA: WebTestClient

    @MockBean
    private lateinit var repository: HumanRepository

    @Test
    fun `Ensure a POST on human API saves it`() {
        given(repository.save(validHuman())).willReturn(validHuman().toMono())
        //When
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(validHuman())).exchange()
        //Then
                .expectStatus().isCreated
                .expectHeader().exists("location")
                .expectBody<Human>().isEqualTo(validHuman())

    }
    @Test
    fun `Ensure a POST with a invalid json returns bad request`() {
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject("{invalid}")).exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Ensure a get will return a list of humans`() {
        val humans = listOf(validHuman(), validHuman()).toFlux()
        given(repository.findAll()).willReturn(humans)
        //When
        makeA.get().uri("/humans").accept(APPLICATION_JSON)
        //Then
                .exchange().expectStatus().isOk
                .expectBodyList(Human::class.java).contains(validHuman())
    }
}


