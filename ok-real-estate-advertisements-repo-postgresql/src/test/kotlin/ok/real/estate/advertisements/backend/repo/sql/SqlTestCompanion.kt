package ok.real.estate.advertisements.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import ok.real.estate.advertisements.common.models.MkplAd
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "estate-pass"
    private const val SCHEMA = "estate"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<MkplAd> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoAdSQL {
        return RepoAdSQL(SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects, randomUuid = randomUuid)
    }
}
