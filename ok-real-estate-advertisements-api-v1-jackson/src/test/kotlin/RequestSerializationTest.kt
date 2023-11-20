package ok.real.estate.advertisements.api.v1

import ok.real.estate.advertisements.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = AdCreateRequest(
        requestId = "123",
        debug = AdDebug(
            mode = AdRequestDebugMode.STUB,
            stub = AdRequestDebugStubs.BAD_TITLE
        ),
        ad = AdCreateObject(
            realEstateType = RealEstateType.FLAT,
            realEstateYear = "2015",
            realEstateArea = "80",
            description = "ad description",
            adStatus = AdStatus.ACTIVE,
            visibility = AdVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"realEstateType\":\\s*\"flat\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as AdCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, AdCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
