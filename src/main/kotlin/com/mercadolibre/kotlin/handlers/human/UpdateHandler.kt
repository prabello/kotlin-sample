package com.mercadolibre.kotlin.handlers.human

import com.mercadolibre.kotlin.helpers.returnNoContent
import com.mercadolibre.kotlin.models.DNA
import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

@Controller
class UpdateHandler(val repository: HumanRepository) {

    fun update(request: ServerRequest): Mono<ServerResponse> = request.bodyToFlux(DNA::class.java)
            .let { makeAList(it) }
            .let { findHumanAndZipWith(it, request) }
            .let { returnHumanWithUpdatedGenome(it) }
            .let { updateHuman(it) }
            .let { returnNoContent() }

//        return request.bodyToFlux(DNA::class.java)
//                .map { it -> mutableListOf(it) }
//                .reduce { initial: MutableList<DNA>, next: MutableList<DNA> ->
//                    initial.addAll(next)
//                    initial
//                }.zipWith(repository.findById(request.pathVariable("id")))
//                .map { it -> it.t2.copy(genome = it.t1) }
//                .flatMap { it -> repository.save(it) }
//                .flatMap { noContent().build() }

    private fun updateHuman(m: Mono<Human>): Mono<Human> {
        return m.flatMap { repository.save(it) }
    }

    private fun returnHumanWithUpdatedGenome(it: Mono<Tuple2<MutableList<DNA>, Human>>): Mono<Human> {
        return it.map { it -> it.t2.sufferMutation(it.t1) }
    }

    private fun findHumanAndZipWith(m: Mono<MutableList<DNA>>, request: ServerRequest): Mono<Tuple2<MutableList<DNA>, Human>> =
            m.zipWith(repository.findById(request.pathVariable("id")))

    private fun makeAList(flux: Flux<DNA>): Mono<MutableList<DNA>> {
        return flux.map { it -> mutableListOf(it) }
                .reduce { initial: MutableList<DNA>, next: MutableList<DNA> ->
                    initial.addAll(next)
                    initial
                }
    }
}