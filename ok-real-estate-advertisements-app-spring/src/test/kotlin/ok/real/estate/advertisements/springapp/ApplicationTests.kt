package ok.real.estate.advertisements.springapp

import com.ninjasquad.springmockk.MockkBean
import ok.real.estate.advertisements.backend.repo.sql.RepoAdSQL
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTests {
    @MockkBean
    private lateinit var repo: RepoAdSQL

    @Test
    fun contextLoads() {
    }
}
