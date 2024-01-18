package ok.real.estate.advertisements.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.stubs.MkplStubs
import ok.real.estate.advertisements.stubs.MkplAdStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AdCreateStubTest {


    private val processor = MkplAdProcessor()
    val id = MkplAdId("666")
    val realEstateType = MkplRealEstateType.FLAT
    val realEstateArea = "82"
    val realEstateYear = "2018"
    val description = "Продается квартира на левом берегу г. Астаны"
    val adStatus = MkplStatus.ACTIVE
    val visibility = MkplVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            adRequest = MkplAd(
                id = id,
                realEstateType = realEstateType,
                realEstateArea = realEstateArea,
                realEstateYear = realEstateYear,
                description = description,
                adStatus = adStatus,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAdStub.get().id, ctx.adResponse.id)
        assertEquals(realEstateType, ctx.adResponse.realEstateType)
        assertEquals(realEstateArea, ctx.adResponse.realEstateArea)
        assertEquals(realEstateYear, ctx.adResponse.realEstateYear)
        assertEquals(description, ctx.adResponse.description)
        assertEquals(adStatus, ctx.adResponse.adStatus)
        assertEquals(visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badRealEstateType() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_TITLE,
            adRequest = MkplAd(
                id = id,
                realEstateType = MkplRealEstateType.NONE,
                realEstateArea = realEstateArea,
                realEstateYear = realEstateYear,
                description = description,
                adStatus = adStatus,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("realEstateType", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_DESCRIPTION,
            adRequest = MkplAd(
                id = id,
                realEstateType = MkplRealEstateType.FLAT,
                realEstateArea = realEstateArea,
                realEstateYear = realEstateYear,
                description = "",
                adStatus = adStatus,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            adRequest = MkplAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_ID,
            adRequest = MkplAd(
                id = id,
                realEstateType = realEstateType,
                realEstateArea = realEstateArea,
                realEstateYear = realEstateYear,
                description = description,
                adStatus = adStatus,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
