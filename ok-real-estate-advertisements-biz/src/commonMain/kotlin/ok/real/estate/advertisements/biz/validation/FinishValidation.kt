package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<MkplContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
