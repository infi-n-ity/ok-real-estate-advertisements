package ok.real.estate.advertisements.biz.validation

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.chain

fun ICorChainDsl<MkplContext>.validation(block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == MkplState.RUNNING }
}
