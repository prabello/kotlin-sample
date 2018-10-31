package com.mercadolibre.kotlin.routers

import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import com.mercadolibre.kotlin.helpers.sampleHuman
import com.mercadolibre.kotlin.models.DNA
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
import org.springframework.web.reactive.function.BodyInserters.*
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@RunWith(SpringRunner::class)
@WebFluxTest
@Import(HumanRouter::class)
internal class InsertHandlerTesst {

    @Autowired
    private lateinit var makeA: WebTestClient

    @MockBean
    private lateinit var repository: HumanRepository

    @Test
    fun `Ensure a POST with a valid JSON will save it`() {
        given(repository.save(sampleHuman())).willReturn(sampleHuman().toMono())
        //When
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(fromObject(sampleHuman())).exchange()
        //Then
                .expectStatus().isCreated
                .expectHeader().exists("location")
                .expectBody<Human>().isEqualTo(sampleHuman())

    }
    @Test
    fun `Ensure a POST with a invalid json returns bad request`() {
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(fromObject("{invalid}")).exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `Ensure a GET with invalid id return a NOT FOUND`(){
        //Given

        //When
        makeA.get().uri("/humans/unknowId").accept(APPLICATION_JSON)
                //Then
                .exchange().expectStatus().isNotFound
                .expectBody().isEmpty
    }

    @Test
    fun `Ensure a GET with a valid ID return a OK with a single human`(){
        given(repository.findById("sampleId")).willReturn(sampleHuman().toMono())
        //When
        makeA.get().uri("/humans/sampleId").accept(APPLICATION_JSON)
                //Then
                .exchange().expectStatus().isOk
                .expectBodyList(Human::class.java).contains(sampleHuman())
    }

    @Test
    fun `Ensure a GET will return a OK with  a list of humans`() {
        val humans = listOf(sampleHuman(), sampleHuman()).toFlux()
        given(repository.findAll()).willReturn(humans)
        //When
        makeA.get().uri("/humans").accept(APPLICATION_JSON)
        //Then
                .exchange().expectStatus().isOk
                .expectBodyList(Human::class.java).contains(sampleHuman())
    }

    @Test
    fun `Ensure a PATCH with a mutant ID will return a MONSTER`(){
        given(repository.findById("sampleId")).willReturn(sampleHuman().toMono())
        val monsterDNA = fromObject(listOf(DNA("MONSTRO"),DNA("FRANGO"),DNA("BIRRL")))
        makeA.patch().uri("/humans/sampleId").accept(APPLICATION_JSON)
                .body(monsterDNA)
                .exchange().expectStatus().isNoContent
    }
}


