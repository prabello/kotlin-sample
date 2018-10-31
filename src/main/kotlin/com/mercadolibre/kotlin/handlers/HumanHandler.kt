package com.mercadolibre.kotlin.handlers

import com.mercadolibre.kotlin.badRequest
import com.mercadolibre.kotlin.createdHuman
import com.mercadolibre.kotlin.models.Human
import com.mercadolibre.kotlin.repositories.HumanRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

private val log = LoggerFactory.getLogger(HumanHandler::class.java)

@Controller
class HumanHandler(private val repository: HumanRepository) {

    fun add(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(Human::class.java)
                .flatMap { repository.save(it) } // IT?
                .flatMap(::createdHuman)

                .onErrorResume {e ->
                    badRequest { log.error("Unable to process request, body is: ${request.bodyToMono<Any>()}", e) }
                }
    }

    fun getAll(request: ServerRequest): Mono<ServerResponse> = ok().body(repository.findAll(), Human::class.java)
}