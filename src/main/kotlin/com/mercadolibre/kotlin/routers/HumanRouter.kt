package com.mercadolibre.kotlin.routers

import com.mercadolibre.kotlin.handlers.HumanHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class HumanRouter{
    @Bean
    fun humanRoutes(humandHandler: HumanHandler) = router {
        (accept(MediaType.APPLICATION_JSON)).nest {
            POST("/humans", humandHandler::add)
            GET("/humans", humandHandler::getAll)
        }
    }
}


