package ok.real.estate.advertisements.api.v1

import ok.real.estate.advertisements.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = AdCreateResponse(
        requestId = "123",
        ad = AdResponseObject(
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
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"realEstateType\":\\s*\"flat\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as AdCreateResponse

        assertEquals(response, obj)
    }
}
