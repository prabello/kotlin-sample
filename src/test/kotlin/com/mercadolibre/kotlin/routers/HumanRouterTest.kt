package com.mercadolibre.kotlin.routers

import com.mercadolibre.kotlin.domains.Human
import com.mercadolibre.kotlin.repositories.Humans
import com.mercadolibre.kotlin.helpers.simpleHuman
import com.mercadolibre.kotlin.domains.DNA
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters.*
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@ExtendWith(SpringExtension::class)
@WebFluxTest
@Import(HumanRouter::class)
@DisplayName("Human API")
internal class HumanRouterTest {

    @Autowired
    private lateinit var makeA: WebTestClient

    @MockBean
    private lateinit var repository: Humans

    @Test
    fun `should save when a valid JSON is POSTed`() {
        given(repository.save(simpleHuman())).willReturn(simpleHuman().toMono())
        //When
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(fromObject(simpleHuman())).exchange()
        //Then
                .expectStatus().isCreated
                .expectHeader().exists("location")
                .expectBody<Human>().isEqualTo(simpleHuman())

    }
    @Test
    fun `should return bad request when a invalid JSON is POSTed`() {
        makeA.post().uri("/humans").contentType(APPLICATION_JSON)
                .body(fromObject("{invalid}")).exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `should return NOT FOUND when a unknow id is GETed`(){
        //Given

        //When
        makeA.get().uri("/humans/unknowId").accept(APPLICATION_JSON)
                //Then
                .exchange().expectStatus().isNotFound
                .expectBody().isEmpty
    }

    @Test
    fun `should return OK with a single human when a valid ID is GETed`(){
        given(repository.findById("sampleId")).willReturn(simpleHuman().toMono())
        //When
        makeA.get().uri("/humans/sampleId").accept(APPLICATION_JSON)
                //Then
                .exchange().expectStatus().isOk
                .expectBodyList(Human::class.java).contains(simpleHuman())
    }

    @Test
    fun `should return OK with a list of humans when GETed`() {
        val humans = listOf(simpleHuman(), simpleHuman()).toFlux()
        given(repository.findAll()).willReturn(humans)
        //When
        makeA.get().uri("/humans").accept(APPLICATION_JSON)
        //Then
                .exchange().expectStatus().isOk
                .expectBodyList(Human::class.java).contains(simpleHuman())
    }

    @Test
    fun `should create a monster when a mutant ID is PATCHed into a human`(){
        given(repository.findById("sampleId")).willReturn(simpleHuman().toMono())
        val monsterDNA = fromObject(listOf(DNA("MONSTRO"), DNA("FRANGO"), DNA("BIRRL")))
        makeA.patch().uri("/humans/sampleId").accept(APPLICATION_JSON)
                .body(monsterDNA)
                .exchange().expectStatus().isNoContent
    }
}


