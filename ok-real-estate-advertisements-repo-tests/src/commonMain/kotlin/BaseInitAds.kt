package ok.real.estate.advertisements.backend.repo.tests

import ok.real.estate.advertisements.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<MkplAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: MkplUserId = MkplUserId("owner-123"),
        adStatus: MkplStatus = MkplStatus.SOLD,
    ) = MkplAd(
        id = MkplAdId("ad-repo-$op-$suf"),
        realEstateType = MkplRealEstateType.FLAT,
        realEstateYear = "$suf stub",
        realEstateArea = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = MkplVisibility.VISIBLE_TO_OWNER,
        adStatus = adStatus,
    )
}
