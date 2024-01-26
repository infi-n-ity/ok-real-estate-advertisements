package ok.real.estate.advertisements.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.DbAdRequest
import ok.real.estate.advertisements.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = MkplAdId("ad-repo-update-not-found")
    protected val lockBad = MkplAdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = MkplAdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        MkplAd(
            id = updateSucc.id,
            realEstateType = MkplRealEstateType.FLAT,
            realEstateYear = "2018",
            realEstateArea = "81",
            description = "update object description",
            ownerId = MkplUserId("owner-123"),
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            adStatus = MkplStatus.SOLD,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = MkplAd(
        id = updateIdNotFound,
        realEstateType = MkplRealEstateType.NONE,
        realEstateYear = "update object not found",
        realEstateArea = "update object not found",
        description = "update object not found description",
        ownerId = MkplUserId("owner-123"),
        visibility = MkplVisibility.VISIBLE_TO_GROUP,
        adStatus = MkplStatus.NONE,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        MkplAd(
            id = updateConc.id,
            realEstateType = MkplRealEstateType.NONE,
            realEstateYear = "update object not found",
            realEstateArea = "update object not found",
            description = "update object not found description",
            ownerId = MkplUserId("owner-123"),
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            adStatus = MkplStatus.NONE,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.realEstateType, result.data?.realEstateType)
        assertEquals(reqUpdateSucc.realEstateYear, result.data?.realEstateYear)
        assertEquals(reqUpdateSucc.realEstateArea, result.data?.realEstateArea)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.adStatus, result.data?.adStatus)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
