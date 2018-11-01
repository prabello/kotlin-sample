package com.mercadolibre.kotlin.handlers

import com.mercadolibre.kotlin.helpers.returnOkWithHuman
import com.mercadolibre.kotlin.domains.Human
import com.mercadolibre.kotlin.repositories.Humans
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

private val log = LoggerFactory.getLogger(SearchHandler::class.java)

@Controller
class SearchHandler(val humans: Humans){
    fun findAllHumans(request: ServerRequest): Mono<ServerResponse> = ok().body(humans.findAll(), Human::class.java)

    fun getPassingParams(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val human = humans.findById(id)
//        val defaultIfEmpty = human.defaultIfEmpty(sampleHuman())
        return ok().body(human, Human::class.java)
    }

    fun findHumanById(request: ServerRequest): Mono<ServerResponse> {
        return request.pathVariable("id").toMono()
                .flatMap { findByIdOrThrowNotFound(it) }
                .flatMap { returnOkWithHuman(it) }
                .switchIfEmpty(notFound().build())
//                .onErrorResume { returnResponseForException(it) } // <- Vai cair no else e retornar 500
    }

    private fun findByIdOrThrowNotFound(id: String): Mono<Human>?{
        @Suppress("USELESS_ELVIS")
        return humans.findById(id) ?: ResponseStatusException(NOT_FOUND,null).toMono()
    }

}