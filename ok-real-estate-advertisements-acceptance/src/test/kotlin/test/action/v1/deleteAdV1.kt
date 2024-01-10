package ru.otus.otuskotlin.real.estate.advertisements.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ok.real.estate.advertisements.api.v1.models.*
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.beValidId
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.beValidLock
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1.debug
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1.haveSuccessResult
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1.sendAndReceive

suspend fun Client.deleteAd(id: String?, lock: String?) {
    withClue("deleteAdV1: $id, lock: $lock") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "ad/delete",
            AdDeleteRequest(
                requestType = "delete",
                debug = debug,
                ad = AdDeleteObject(id = id, lock = lock)
            )
        ) as AdDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.ad shouldNotBe null
            response.ad?.id shouldBe id
        }
    }
}