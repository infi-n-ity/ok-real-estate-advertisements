package ok.real.estate.advertisements.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.stubs.MkplStubs
import ok.real.estate.advertisements.stubs.MkplAdStub

fun ICorChainDsl<MkplContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    handle {
        state = MkplState.FINISHING
        val stub = MkplAdStub.prepareResult {
            adRequest.id.takeIf { it != MkplAdId.NONE }?.also { this.id = it }
            adRequest.realEstateType.takeIf { it != MkplRealEstateType.NONE }?.also { this.realEstateType = it }
            adRequest.realEstateArea.takeIf { it.isNotBlank() }?.also { this.realEstateArea = it }
            adRequest.realEstateYear.takeIf { it.isNotBlank() }?.also { this.realEstateYear = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.adStatus.takeIf { it != MkplStatus.NONE }?.also { this.adStatus = it }
            adRequest.visibility.takeIf { it != MkplVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
