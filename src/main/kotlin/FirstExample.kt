import org.neo4j.driver.v1.AuthTokens
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.neo4j.driver.v1.Session


val createRecords = { session: Session ->
    session.run("CREATE (a:Person {name:'Arthur', title:'King'})")
}

val displayRecords = { session: Session ->
    val result = session.run("MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title")
    while (result.hasNext()) {
        val record = result.next()
        println(record.get("title").asString() + " " + record.get("name").asString())
    }
}

fun useDriver(driver: Driver, vararg ops: (Session) -> Any) {
    val session = driver.session()
    ops.map { it(session) }
    session.close()
    driver.close()
}

fun main(args: Array<String>) {
    useDriver(GraphDatabase.driver("bolt://192.168.99.100", AuthTokens.basic("neo4j", "admin")),
            createRecords,
            displayRecords)
}