package ok.real.estate.advertisements.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ok.real.estate.advertisements.biz.MkplAdProcessor

@Configuration
class CorConfig {
    @Bean
    fun processor() = MkplAdProcessor()
}
