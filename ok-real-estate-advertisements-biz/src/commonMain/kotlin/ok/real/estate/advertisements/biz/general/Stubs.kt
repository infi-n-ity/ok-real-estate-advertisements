package ok.real.estate.advertisements.biz.general

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.common.models.MkplWorkMode
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.chain

fun ICorChainDsl<MkplContext>.stubs(title: String, block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == MkplWorkMode.STUB && state == MkplState.RUNNING }
}
