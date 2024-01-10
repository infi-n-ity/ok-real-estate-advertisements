package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ok.real.estate.advertisements.api.v1.models.*
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client

suspend fun Client.createAd(ad: AdCreateObject = someCreateAd): AdResponseObject = createAd(ad) {
    it should haveSuccessResult
    it.ad shouldNotBe null
    it.ad?.apply {
        realEstateType shouldBe ad.realEstateType
        realEstateYear shouldBe ad.realEstateYear
        realEstateArea shouldBe ad.realEstateArea
        description shouldBe ad.description
        adStatus shouldBe ad.adStatus
        visibility shouldBe ad.visibility
    }
    it.ad!!
}

suspend fun <T> Client.createAd(ad: AdCreateObject = someCreateAd, block: (AdCreateResponse) -> T): T =
    withClue("createAdV1: $ad") {
        val response = sendAndReceive(
            "ad/create", AdCreateRequest(
                requestType = "create",
                debug = debug,
                ad = ad
            )
        ) as AdCreateResponse

        response.asClue(block)
    }
