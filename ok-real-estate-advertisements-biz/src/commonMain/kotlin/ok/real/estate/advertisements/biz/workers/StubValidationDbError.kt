package ok.real.estate.advertisements.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplError
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.common.stubs.MkplStubs

fun ICorChainDsl<MkplContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.DB_ERROR && state == MkplState.RUNNING }
    handle {
        state = MkplState.FAILING
        this.errors.add(
            MkplError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
