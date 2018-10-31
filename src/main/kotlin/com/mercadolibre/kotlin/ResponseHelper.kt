package com.mercadolibre.kotlin

import com.mercadolibre.kotlin.models.Human
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.URI

fun createdHuman(human: Human): Mono<ServerResponse> {
    return ServerResponse.created(URI("${human.id}"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(human.toMono(), Human::class.java)
}

fun badRequest(unit: () -> Unit): Mono<ServerResponse> {
    unit.invoke() //<- exemplo por falta de algo melhor
    return badRequest().build()
}
