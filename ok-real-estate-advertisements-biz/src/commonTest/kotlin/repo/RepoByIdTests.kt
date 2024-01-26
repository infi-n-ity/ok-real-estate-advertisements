package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.backend.repo.tests.AdRepositoryMock
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdResponse
import kotlin.test.assertEquals

private val initAd = MkplAd(
    id = MkplAdId("123"),
    realEstateType = MkplRealEstateType.FLAT,
    realEstateYear = "2018",
    realEstateArea = "81",
    description = "abc",
    adStatus = MkplStatus.SOLD,
    visibility = MkplVisibility.VISIBLE_PUBLIC,
)
private val repo = AdRepositoryMock(
        invokeReadAd = {
            if (it.id == initAd.id) {
                DbAdResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbAdResponse(
                isSuccess = false,
                data = null,
                errors = listOf(MkplError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    MkplCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { MkplAdProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: MkplCommand) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = MkplAdId("12345"),
            realEstateType = MkplRealEstateType.FLAT,
            realEstateYear = "xyz",
            realEstateArea = "82",
            description = "xyz",
            adStatus = MkplStatus.SOLD,
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
        ),
    )
    processor.exec(ctx)
    assertEquals(MkplState.FAILING, ctx.state)
    assertEquals(MkplAd(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
