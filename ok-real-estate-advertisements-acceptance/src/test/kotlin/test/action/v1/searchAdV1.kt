package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ok.real.estate.advertisements.api.v1.models.AdResponseObject
import ok.real.estate.advertisements.api.v1.models.AdSearchFilter
import ok.real.estate.advertisements.api.v1.models.AdSearchRequest
import ok.real.estate.advertisements.api.v1.models.AdSearchResponse
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client

suspend fun Client.searchAd(search: AdSearchFilter): List<AdResponseObject> = searchAd(search) {
    it should haveSuccessResult
    it.ads ?: listOf()
}

suspend fun <T> Client.searchAd(search: AdSearchFilter, block: (AdSearchResponse) -> T): T =
    withClue("searchAdV1: $search") {
        val response = sendAndReceive(
            "ad/search",
            AdSearchRequest(
                requestType = "search",
                debug = debug,
                adFilter = search,
            )
        ) as AdSearchResponse

        response.asClue(block)
    }
