package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.helpers.errorValidation
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.fail

fun ICorChainDsl<MkplContext>.validateRealEstateAreaHasOnlyDigit(title: String) = worker {
    this.title = title
    on { adValidating.realEstateArea.isNotEmpty() && !adValidating.realEstateArea.matches("\\d+".toRegex()) }
    handle {
        fail(
            errorValidation(
                field = "realEstateArea",
                violationCode = "digit-error",
                description = "field must contain digits"
            )
        )
    }
}
