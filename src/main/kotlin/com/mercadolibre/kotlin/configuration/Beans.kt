@file:Suppress("unused")

package com.mercadolibre.kotlin.configuration

import com.mercadolibre.kotlin.handlers.HumanHandler
//import com.mercadolibre.kotlin.routers.humanRoutes
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

//class Beans : ApplicationContextInitializer<GenericApplicationContext>{
//    override fun initialize(applicationContext: GenericApplicationContext) {
//        beans {
//            bean { humanRoutes(ref()) }
//            bean { HumanHandler(ref()) }
//        }.initialize(applicationContext)
//    }
//}