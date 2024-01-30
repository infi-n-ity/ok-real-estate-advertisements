package ok.real.estate.advertisements.springapp.config

import ok.real.estate.advertisements.backend.repo.sql.RepoAdSQL
import ok.real.estate.advertisements.backend.repo.sql.SqlProperties
import ok.real.estate.advertisements.backend.repository.inmemory.AdRepoStub
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.repo.IAdRepository
import ok.real.estate.advertisements.repo.inmemory.AdRepoInMemory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class CorConfig {
    @Bean
    fun processor(corSettings: MkplCorSettings) = MkplAdProcessor(corSettings)


    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IAdRepository?,
        @Qualifier("testRepository") testRepository: IAdRepository,
        @Qualifier("stubRepository") stubRepository: IAdRepository,
    ): MkplCorSettings = MkplCorSettings(
        repoStub = stubRepository,
        repoTest = testRepository,
        repoProd = prodRepository ?: testRepository,
    )

    @Bean(name = ["prodRepository"])
    @ConditionalOnProperty(value = ["prod-repository"], havingValue = "sql")
    fun prodRepository(sqlProperties: SqlProperties) = RepoAdSQL(sqlProperties)

    @Bean
    fun testRepository() = AdRepoInMemory()

    @Bean
    fun stubRepository() = AdRepoStub()
}
