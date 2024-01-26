package ok.real.estate.advertisements.springapp.config

import ok.real.estate.advertisements.backend.repository.inmemory.AdRepoStub
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.repo.inmemory.AdRepoInMemory

@Configuration
class CorConfig {
    @Bean
    fun processor() = MkplAdProcessor(corSettings())

    @Bean
    fun corSettings(): MkplCorSettings = MkplCorSettings(
        repoStub = AdRepoStub(),
        repoProd = AdRepoInMemory(),
        repoTest = AdRepoInMemory(),
    )
}
