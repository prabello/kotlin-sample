package com.mercadolibre.kotlin.helpers

import com.mercadolibre.kotlin.exceptions.LockedException
import com.mercadolibre.kotlin.models.Human
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.ClassCastException
import java.net.URI

fun returnCreatedHumanResponse(human: Human): Mono<ServerResponse> {
    return ServerResponse.created(URI("${human.id}"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(human.toMono(), Human::class.java)
}

fun badRequest(unit: () -> Unit): Mono<ServerResponse> {
    unit.invoke() //<- exemplo por falta de algo melhor
    return badRequest().build()
}

fun returnResponseForException(it: Throwable?): Mono<out ServerResponse>? {
    return when (it) {
        is IllegalArgumentException -> badRequest().build()
        is ClassCastException -> ServerResponse.unprocessableEntity().build()
        is LockedException -> ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).build()
        else -> ServerResponse.notFound().build()
    }
}

fun returnNoContent(): Mono<ServerResponse> {
    return ServerResponse.noContent().build()
}