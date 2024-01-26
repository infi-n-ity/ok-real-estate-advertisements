package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import ok.real.estate.advertisements.backend.repo.tests.AdRepositoryMock
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.READ
    private val initAd = MkplAd(
        id = MkplAdId("123"),
        realEstateType = MkplRealEstateType.FLAT,
        realEstateYear = "abc",
        realEstateArea = "abc",
        description = "abc",
        ownerId = userId,
        adStatus = MkplStatus.SOLD,
        visibility = MkplVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd,
            )
        }
    ) }
    private val settings by lazy {
        MkplCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAd(
                id = MkplAdId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.realEstateType, ctx.adResponse.realEstateType)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adStatus, ctx.adResponse.adStatus)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
