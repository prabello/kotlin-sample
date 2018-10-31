package com.mercadolibre.kotlin.handlers.human

import com.mercadolibre.kotlin.helpers.badRequest
import com.mercadolibre.kotlin.helpers.returnCreatedHumanResponse
import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.Humans
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

private val log = LoggerFactory.getLogger(InsertHandler::class.java)

@Controller
class InsertHandler(private val humans: Humans) {

    fun add(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Human::class.java)
                .flatMap { humans.save(it) }
                .flatMap { returnCreatedHumanResponse(it) }
                .onErrorResume { badRequest().build() }
    }

    fun add2(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Human::class.java)
                .flatMap { human: Human -> humans.save(human) }
                .flatMap(::returnCreatedHumanResponse)
                .onErrorResume { e ->
                    badRequest(e) { log.error("Unable to process request, body is: ${request.bodyToMono<Any>()}") }
                }
        //      .onErrorReturn(badRequest().build()) <- Faz um mono do que for retornar
    }
}