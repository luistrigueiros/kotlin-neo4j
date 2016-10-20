package com.example

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.stream.Stream

@NodeEntity
data class Person(var first: String? = null,
                  var last : String? = null,
                  @GraphId var id : Long? = null)

interface  PersonRepository : CrudRepository<Person, String>

@SpringBootApplication
@EnableTransactionManagement
@EnableNeo4jRepositories("com.example")
open class DemoApplication(val personRepository: PersonRepository) : CommandLineRunner {

    override fun run(vararg p0: String?) {
        personRepository.deleteAll()
        Stream.of("Luis Trigueiros", "Candida Trigueiros", "Afonso Trigueiros", "Constanca Trigueiros")
                .map { it.split(" ") }
                .forEach {
                    val firstname = it[0]
                    val last = it[1]
                    personRepository.save( Person(firstname, last))
                }
        Stream.of(personRepository.findAll()).forEach(::println)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}
