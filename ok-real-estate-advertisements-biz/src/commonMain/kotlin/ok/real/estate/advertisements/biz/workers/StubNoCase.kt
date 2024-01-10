package ru.otus.otuskotlin.marketplace.biz.workers

import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.fail
import ok.real.estate.advertisements.common.models.MkplError
import ok.real.estate.advertisements.common.models.MkplState

fun ICorChainDsl<MkplContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        fail(
            MkplError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
