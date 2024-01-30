package ok.real.estate.advertisements.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.biz.repo.repoNotFoundTest
import ok.real.estate.advertisements.backend.repo.tests.AdRepositoryMock
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.DELETE
    private val initAd = MkplAd(
        id = MkplAdId("123"),
        realEstateType = MkplRealEstateType.FLAT,
        realEstateYear = "abc",
        realEstateArea = "abc",
        description = "abc",
        ownerId = userId,
        adStatus = MkplStatus.SOLD,
        visibility = MkplVisibility.VISIBLE_PUBLIC,
        lock = MkplAdLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        AdRepositoryMock(
            invokeReadAd = {
               DbAdResponse(
                   isSuccess = true,
                   data = initAd,
               )
            },
            invokeDeleteAd = {
                if (it.id == initAd.id)
                    DbAdResponse(
                        isSuccess = true,
                        data = initAd
                    )
                else DbAdResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        MkplCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplAdProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = MkplAd(
            id = MkplAdId("123"),
            lock = MkplAdLock("123-234-abc-ABC"),
        )
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.realEstateType, ctx.adResponse.realEstateType)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adStatus, ctx.adResponse.adStatus)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
