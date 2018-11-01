package com.mercadolibre.kotlin.repositories

import com.mercadolibre.kotlin.domains.Human
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface Humans : ReactiveMongoRepository<Human, String>
