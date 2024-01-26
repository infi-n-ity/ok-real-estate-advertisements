package ok.real.estate.advertisements.backend.repo.tests

import ok.real.estate.advertisements.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<MkplAd> {

    open val lockOld: MkplAdLock = MkplAdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: MkplAdLock = MkplAdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: MkplUserId = MkplUserId("owner-123"),
        adStatus: MkplStatus = MkplStatus.SOLD,
        lock: MkplAdLock = lockOld,
    ) = MkplAd(
        id = MkplAdId("ad-repo-$op-$suf"),
        realEstateType = MkplRealEstateType.FLAT,
        realEstateYear = "$suf stub",
        realEstateArea = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = MkplVisibility.VISIBLE_TO_OWNER,
        adStatus = adStatus,
        lock = lock,
    )
}
