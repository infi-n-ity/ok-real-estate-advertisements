package ok.real.estate.advertisements.biz.repo

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == MkplState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}
