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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.UPDATE
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
        },
        invokeUpdateAd = {
            DbAdResponse(
                isSuccess = true,
                data = MkplAd(
                    id = MkplAdId("123"),
                    realEstateType = MkplRealEstateType.FLAT,
                    realEstateYear = "xyz",
                    realEstateArea = "xyz",
                    description = "xyz",
                    adStatus = MkplStatus.SOLD,
                    visibility = MkplVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    ) }
    private val settings by lazy {
        MkplCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplAdProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = MkplAd(
            id = MkplAdId("123"),
            realEstateType = MkplRealEstateType.FLAT,
            realEstateYear = "xyz",
            realEstateArea = "82",
            description = "xyz",
            adStatus = MkplStatus.SOLD,
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
        )
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.adResponse.id)
        assertEquals(adToUpdate.realEstateType, ctx.adResponse.realEstateType)
        assertEquals(adToUpdate.description, ctx.adResponse.description)
        assertEquals(adToUpdate.adStatus, ctx.adResponse.adStatus)
        assertEquals(adToUpdate.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
