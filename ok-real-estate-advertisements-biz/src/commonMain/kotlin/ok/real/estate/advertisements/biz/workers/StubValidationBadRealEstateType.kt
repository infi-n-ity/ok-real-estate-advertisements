package ok.real.estate.advertisements.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplError
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.common.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubValidationBadRealEstateType(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.BAD_TITLE && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        this.errors.add(
            MkplError(
                group = "validation",
                code = "validation-real-estate-type",
                field = "realEstateType",
                message = "Wrong realEstateType field"
            )
        )
    }
}
