package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.errorValidation
import ok.real.estate.advertisements.common.helpers.fail
import ok.real.estate.advertisements.common.models.MkplAdLock
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.lock != MkplAdLock.NONE && !adValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
