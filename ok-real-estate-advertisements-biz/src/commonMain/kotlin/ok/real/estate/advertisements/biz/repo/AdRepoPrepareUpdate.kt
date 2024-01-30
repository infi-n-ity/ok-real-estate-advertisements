package ok.real.estate.advertisements.biz.repo

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == MkplState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy().apply {
            this.realEstateType = adValidated.realEstateType
            realEstateYear = adValidated.realEstateYear
            realEstateArea = adValidated.realEstateArea
            description = adValidated.description
            adStatus = adValidated.adStatus
            visibility = adValidated.visibility
        }
    }
}
