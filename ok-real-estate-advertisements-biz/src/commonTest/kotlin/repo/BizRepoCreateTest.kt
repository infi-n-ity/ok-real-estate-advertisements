package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.backend.repo.tests.AdRepositoryMock
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = AdRepositoryMock(
        invokeCreateAd = {
            DbAdResponse(
                isSuccess = true,
                data = MkplAd(
                    id = MkplAdId(uuid),
                    realEstateType = it.ad.realEstateType,
                    realEstateYear = it.ad.realEstateYear,
                    realEstateArea = it.ad.realEstateArea,
                    description = it.ad.description,
                    ownerId = userId,
                    adStatus = it.ad.adStatus,
                    visibility = it.ad.visibility,
                )
            )
        }
    )
    private val settings = MkplCorSettings(
        repoTest = repo
    )
    private val processor = MkplAdProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAd(
                realEstateType = MkplRealEstateType.FLAT,
                realEstateYear = "2018",
                realEstateArea = "81",
                description = "abc",
                adStatus = MkplStatus.SOLD,
                visibility = MkplVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertNotEquals(MkplAdId.NONE, ctx.adResponse.id)
        assertEquals(MkplRealEstateType.FLAT, ctx.adResponse.realEstateType)
        assertEquals("abc", ctx.adResponse.description)
        assertEquals(MkplStatus.SOLD, ctx.adResponse.adStatus)
        assertEquals(MkplVisibility.VISIBLE_PUBLIC, ctx.adResponse.visibility)
    }
}
