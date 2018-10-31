package com.mercadolibre.kotlin.handlers

import com.mercadolibre.kotlin.helpers.badRequest
import com.mercadolibre.kotlin.helpers.returnCreatedHumanResponse
import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

private val log = LoggerFactory.getLogger(InsertHandler::class.java)

@Controller
class InsertHandler(private val repository: HumanRepository) {

    fun add(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Human::class.java)
                .flatMap { repository.save(it) }
                .flatMap { returnCreatedHumanResponse(it) }
                .onErrorResume { badRequest().build() }
    }

    fun add2(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Human::class.java)
                .flatMap { human: Human -> repository.save(human) }
                .flatMap(::returnCreatedHumanResponse)
                .onErrorResume { e ->
                    badRequest { log.error("Unable to process request, body is: ${request.bodyToMono<Any>()}", e) }
                }
        //      .onErrorReturn(badRequest().build())
    }
}