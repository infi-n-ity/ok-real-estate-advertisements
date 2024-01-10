package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.shouldBe
import ok.real.estate.advertisements.api.v1.models.AdSearchFilter
import ok.real.estate.advertisements.api.v1.models.AdStatus
import ok.real.estate.advertisements.api.v1.models.AdUpdateObject
import ok.real.estate.advertisements.api.v1.models.RealEstateType
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.client.Client
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1.*
import ru.otus.otuskotlin.real.estate.advertisements.test.action.v1.deleteAd

fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {
        test("Create Ad ok") {
            client.createAd()
        }

        test("Read Ad ok") {
            val created = client.createAd()
            client.readAd(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Ad ok") {
            val created = client.createAd()
            client.updateAd(created.id, created.lock, AdUpdateObject(realEstateType = RealEstateType.HOUSE))
            client.readAd(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                //it.ad?.title shouldBe "Selling Nut"
                //it.ad?.description shouldBe someCreateAd.description
            }
        }

        test("Delete Ad ok") {
            val created = client.createAd()
            client.deleteAd(created.id, created.lock)
            client.readAd(created.id) {
                // it should haveError("not-found") TODO раскомментировать, когда будет реальный реп
            }
        }

        test("Search Ad ok") {
            val created1 = client.createAd(someCreateAd.copy(realEstateType = RealEstateType.FLAT))
            val created2 = client.createAd(someCreateAd.copy(realEstateType = RealEstateType.HOUSE))

            withClue("Search Selling") {
                val results = client.searchAd(search = AdSearchFilter(searchString = "Astana"))
                // TODO раскомментировать, когда будет реальный реп
                // results shouldHaveSize 2
                // results shouldExist { it.title == "Selling Bolt" }
                // results shouldExist { it.title == "Selling Nut" }
            }

            withClue("Search Bolt") {
                client.searchAd(search = AdSearchFilter(searchString = "Bolt"))
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({ it.title == "Selling Bolt" })
            }
        }
    }

}