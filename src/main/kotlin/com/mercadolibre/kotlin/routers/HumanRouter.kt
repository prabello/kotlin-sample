package com.mercadolibre.kotlin.routers

import com.mercadolibre.kotlin.handlers.InsertHandler
import com.mercadolibre.kotlin.handlers.SearchHandler
import com.mercadolibre.kotlin.handlers.UpdateHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class HumanRouter{
    @Bean
    fun humanRoutes(insertHandler: InsertHandler, updateHandler: UpdateHandler, searchHandler: SearchHandler) = router {
        (accept(MediaType.APPLICATION_JSON)).nest {
            POST("/humans", insertHandler::add)
            GET("/humans", searchHandler::findAllHumans)
            GET("/humans/{id}",searchHandler::findHumanById)
            PATCH("/humans/{id}",updateHandler::update)
        }
    }
}


