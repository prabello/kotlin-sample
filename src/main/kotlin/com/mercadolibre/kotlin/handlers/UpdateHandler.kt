package com.mercadolibre.kotlin.handlers

import com.mercadolibre.kotlin.helpers.returnNoContent
import com.mercadolibre.kotlin.domains.DNA
import com.mercadolibre.kotlin.domains.Human
import com.mercadolibre.kotlin.repositories.Humans
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.noContent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

@Controller
class UpdateHandler(val humans: Humans) {

    fun update(request: ServerRequest): Mono<ServerResponse> =
            request.bodyToFlux(DNA::class.java)
            .let { makeAList(it) }
            .let { findHumanAndZipWith(it, request) }
            .let { returnHumanWithUpdatedGenome(it) }
            .let { updateHuman(it) }
            .let { returnNoContent() }

//        request.bodyToFlux(DNA::class.java)
//                .map { it -> mutableListOf(it) }
//                .reduce { initial: MutableList<DNA>, next: MutableList<DNA> ->
//                    initial.addAll(next)
//                    initial
//                }.zipWith(humans.findById(request.pathVariable("id")))
//                .map { it -> it.t2.copy(genome = it.t1) }
//                .flatMap { it -> humans.save(it) }
//                .flatMap { noContent().build() }

    private fun updateHuman(m: Mono<Human>): Mono<Human> {
        return m.flatMap { humans.save(it) }
    }

    private fun returnHumanWithUpdatedGenome(tuple: Mono<Tuple2<MutableList<DNA>, Human>>): Mono<Human> {
        //E se no lugar de tuple for it?
        return tuple.map { it -> it.t2.sufferMutation(it.t1) }
    }

    private fun findHumanAndZipWith(m: Mono<MutableList<DNA>>, request: ServerRequest): Mono<Tuple2<MutableList<DNA>, Human>> =
            m.zipWith(humans.findById(request.pathVariable("id")))

    private fun makeAList(flux: Flux<DNA>): Mono<MutableList<DNA>> {
        return flux.map { it -> mutableListOf(it) }
                .reduce { initial: MutableList<DNA>, next: MutableList<DNA> ->
                    initial.addAll(next)
                    initial
                }
    }
}