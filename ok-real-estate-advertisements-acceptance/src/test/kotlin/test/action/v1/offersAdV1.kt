package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ok.real.estate.advertisements.api.v1.models.*
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client

suspend fun Client.offersAd(id: String?): List<AdResponseObject> = offersAd(id) {
    it should haveSuccessResult
    it.ads ?: listOf()
}

suspend fun <T> Client.offersAd(id: String?, block: (AdOffersResponse) -> T): T =
    withClue("searchOffersV1: $id") {
        val response = sendAndReceive(
            "ad/offers",
            AdOffersRequest(
                requestType = "offers",
                debug = debug,
                ad = AdReadObject(id = id),
            )
        ) as AdOffersResponse

        response.asClue(block)
    }
