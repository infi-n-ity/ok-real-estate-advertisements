package ok.real.estate.advertisements.mappers.v1

import org.junit.Test
import ok.real.estate.advertisements.api.v1.models.*
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.stubs.MkplStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS,
            ),
            ad = AdCreateObject(
                realEstateType = RealEstateType.HOUSE,
                realEstateYear = "2020",
                realEstateArea = "300",
                description = "desc",
                adStatus = AdStatus.ACTIVE,
                visibility = AdVisibility.PUBLIC,
            ),
        )

        val context = MkplContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(MkplRealEstateType.HOUSE, context.adRequest.realEstateType)
        assertEquals(MkplVisibility.VISIBLE_PUBLIC, context.adRequest.visibility)
        assertEquals(MkplStatus.ACTIVE, context.adRequest.adStatus)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            adResponse = MkplAd(
                realEstateType = MkplRealEstateType.HOUSE,
                realEstateYear = "2020",
                realEstateArea = "300",
                description = "desc",
                adStatus = MkplStatus.ACTIVE,
                visibility = MkplVisibility.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportAd() as AdCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals(RealEstateType.HOUSE, req.ad?.realEstateType)
        assertEquals("2020", req.ad?.realEstateYear)
        assertEquals("300", req.ad?.realEstateArea)
        assertEquals("desc", req.ad?.description)
        assertEquals(AdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(AdStatus.ACTIVE, req.ad?.adStatus)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
