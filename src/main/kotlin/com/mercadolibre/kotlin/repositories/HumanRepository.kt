package com.mercadolibre.kotlin.repositories

import com.mercadolibre.kotlin.models.Human
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface HumanRepository : ReactiveMongoRepository<Human, String>
