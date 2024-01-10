package ok.real.estate.advertisements.biz.groups

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplCommand
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.chain

fun ICorChainDsl<MkplContext>.operation(title: String, command: MkplCommand, block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == MkplState.RUNNING }
}
