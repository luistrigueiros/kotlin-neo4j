import org.neo4j.driver.v1.AuthTokens
import org.neo4j.driver.v1.GraphDatabase
import org.neo4j.driver.v1.Session


val createRecords = {session: Session ->
    session.run("CREATE (a:Person {name:'Arthur', title:'King'})")
}

val displayRecords = { session : Session ->
    val result = session.run("MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title")
    while (result.hasNext()) {
        val record = result.next()
        println(record.get("title").asString() + " " + record.get("name").asString())
    }
}


fun main(args: Array<String>) {

    val driver = GraphDatabase.driver("bolt://192.168.99.100", AuthTokens.basic("neo4j", "admin"))
    val session = driver.session()

    createRecords(session)
    displayRecords(session)

    session.close()
    driver.close()

}