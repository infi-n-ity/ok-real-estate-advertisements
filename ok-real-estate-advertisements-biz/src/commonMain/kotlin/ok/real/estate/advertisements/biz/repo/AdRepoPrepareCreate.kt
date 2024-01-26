package ok.real.estate.advertisements.biz.repo

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == MkplState.RUNNING }
    handle {
        adRepoRead = adValidated.deepCopy()
        adRepoPrepare = adRepoRead

    }
}
