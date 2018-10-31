package com.mercadolibre.kotlin.handlers.human

import com.mercadolibre.kotlin.helpers.returnResponseForException
import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Controller
class SearchHandler(val repository: HumanRepository){
    fun findAllHumans(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().body(repository.findAll(), Human::class.java)

    fun getPassingParams(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val human = repository.findById(id) ?: return notFound().build()
//        val defaultIfEmpty = human.defaultIfEmpty(sampleHuman())
        return ServerResponse.ok().body(human, Human::class.java)
    }

    fun findHumanById(request: ServerRequest): Mono<ServerResponse> {
        return request.pathVariable("id").toMono()
                .flatMap { repository.findById(it) }
//                .defaultIfEmpty( sampleHuman())
                .flatMap { ServerResponse.ok().body(it.toMono(), Human::class.java) }
                .onErrorResume { returnResponseForException(it) }
    }
}