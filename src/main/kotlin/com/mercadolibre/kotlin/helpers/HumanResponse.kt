package com.mercadolibre.kotlin.helpers

import com.mercadolibre.kotlin.exceptions.LockedException
import com.mercadolibre.kotlin.domains.Human
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.ClassCastException
import java.net.URI

fun returnCreatedHumanResponse(human: Human): Mono<ServerResponse> {
    return created(URI("${human.id}"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(human.toMono(), Human::class.java)
}

fun badRequest(e: Throwable, unit: () -> Unit): Mono<ServerResponse> {
    e.printStackTrace()
    unit.invoke() //<- exemplo por falta de algo melhor
    return badRequest().build()
}

fun returnResponseForException(exception: Throwable?): Mono<ServerResponse>? {
    return when (exception) {
        is IllegalArgumentException -> badRequest().build()
        is ClassCastException -> unprocessableEntity().build()
        is LockedException -> status(HttpStatus.TOO_MANY_REQUESTS).build()
        else -> status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}

fun sampleInline(it: Throwable?) = when (it) {
    is IllegalArgumentException -> badRequest().build()
    is ClassCastException -> unprocessableEntity().build()
    is LockedException -> status(HttpStatus.TOO_MANY_REQUESTS).build()
    else -> status(HttpStatus.INTERNAL_SERVER_ERROR).build()
}


fun returnOkWithHuman(it: Human) = ok().body(it.toMono(), Human::class.java)

fun returnNoContent() = noContent().build()