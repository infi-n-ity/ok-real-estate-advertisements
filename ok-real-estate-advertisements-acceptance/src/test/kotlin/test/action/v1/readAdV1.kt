package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ok.real.estate.advertisements.api.v1.models.AdReadObject
import ok.real.estate.advertisements.api.v1.models.AdReadRequest
import ok.real.estate.advertisements.api.v1.models.AdReadResponse
import ok.real.estate.advertisements.api.v1.models.AdResponseObject
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.beValidId
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client

suspend fun Client.readAd(id: String?): AdResponseObject = readAd(id) {
    it should haveSuccessResult
    it.ad shouldNotBe null
    it.ad!!
}

suspend fun <T> Client.readAd(id: String?, block: (AdReadResponse) -> T): T =
    withClue("readAdV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "ad/read",
            AdReadRequest(
                requestType = "read",
                debug = debug,
                ad = AdReadObject(id = id)
            )
        ) as AdReadResponse

        response.asClue(block)
    }
