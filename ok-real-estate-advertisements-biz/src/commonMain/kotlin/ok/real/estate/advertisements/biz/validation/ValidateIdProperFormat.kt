package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.helpers.errorValidation
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.fail
import ok.real.estate.advertisements.common.models.MkplAdId

fun ICorChainDsl<MkplContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.id != MkplAdId.NONE && !adValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
