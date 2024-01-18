package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.helpers.errorValidation
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.fail

fun ICorChainDsl<MkplContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
