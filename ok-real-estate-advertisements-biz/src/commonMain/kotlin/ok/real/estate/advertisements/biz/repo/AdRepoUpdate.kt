package ok.real.estate.advertisements.biz.repo

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.common.repo.DbAdRequest
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.updateAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = MkplState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
