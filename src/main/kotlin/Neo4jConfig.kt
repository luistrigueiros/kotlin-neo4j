package com.example

import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by luist on 20/10/2016.
 */
@Configuration
open class Config {
    @Bean
    open fun sessionFactory(): SessionFactory {
        return SessionFactory("com.example")
    }

    @Bean
    open fun configuration(): org.neo4j.ogm.config.Configuration {
        val config = org.neo4j.ogm.config.Configuration()
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://neo4j:admin@192.168.99.100:7474")
        return config
    }

}