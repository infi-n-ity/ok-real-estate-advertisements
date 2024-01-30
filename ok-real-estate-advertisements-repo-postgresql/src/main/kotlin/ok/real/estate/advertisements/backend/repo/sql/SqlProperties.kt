package ok.real.estate.advertisements.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/estate",
    val user: String = "postgres",
    val password: String = "estate-pass",
    val schema: String = "estate",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
