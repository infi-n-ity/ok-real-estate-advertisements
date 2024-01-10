package ru.otus.otuskotlin.marketplace.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplRealEstateType
import ok.real.estate.advertisements.common.models.MkplState
import ok.real.estate.advertisements.common.stubs.MkplStubs
import ok.real.estate.advertisements.stubs.MkplAdStub

fun ICorChainDsl<MkplContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == MkplStubs.SUCCESS && state == MkplState.RUNNING }
    handle {
        state = MkplState.FINISHING
        val stub = MkplAdStub.prepareResult {
            adRequest.realEstateType.takeIf { it != MkplRealEstateType.NONE }?.also { this.realEstateType = it }
        }
        adResponse = stub
    }
}
