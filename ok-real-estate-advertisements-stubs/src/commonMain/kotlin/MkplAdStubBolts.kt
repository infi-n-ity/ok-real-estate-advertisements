package ok.real.estate.advertisements.stubs

import ok.real.estate.advertisements.common.models.*

object MkplAdStubFlats {
    val AD_ACTIVE_FLAT1: MkplAd
        get() = MkplAd(
            id = MkplAdId("666"),
            realEstateType = MkplRealEstateType.FLAT,
            realEstateYear = "2018",
            realEstateArea = "82",
            description = "Продается квартира на левом берегу г. Астаны",
            ownerId = MkplUserId("user-1"),
            adStatus = MkplStatus.ACTIVE,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                MkplAdPermissionClient.READ,
                MkplAdPermissionClient.UPDATE,
                MkplAdPermissionClient.DELETE,
                MkplAdPermissionClient.MAKE_VISIBLE_PUBLIC,
                MkplAdPermissionClient.MAKE_VISIBLE_GROUP,
                MkplAdPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
    val AD_SOLD_FLAT1 = AD_ACTIVE_FLAT1.copy(adStatus = MkplStatus.SOLD)
}
