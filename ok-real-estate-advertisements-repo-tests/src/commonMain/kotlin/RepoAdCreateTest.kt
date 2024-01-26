package ok.real.estate.advertisements.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdRequest
import ok.real.estate.advertisements.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    private val createObj = MkplAd(
        realEstateType = MkplRealEstateType.FLAT,
        realEstateYear = "2018",
        realEstateArea = "81",
        description = "create object description",
        ownerId = MkplUserId("owner-123"),
        visibility = MkplVisibility.VISIBLE_TO_GROUP,
        adStatus = MkplStatus.SOLD,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: MkplAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.realEstateType, result.data?.realEstateType)
        assertEquals(expected.realEstateYear, result.data?.realEstateYear)
        assertEquals(expected.realEstateArea, result.data?.realEstateArea)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adStatus, result.data?.adStatus)
        assertNotEquals(MkplAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<MkplAd> = emptyList()
    }
}
