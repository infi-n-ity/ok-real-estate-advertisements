package ok.real.estate.advertisements.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState

fun ICorChainDsl<MkplContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == MkplState.NONE }
    handle { state = MkplState.RUNNING }
}
