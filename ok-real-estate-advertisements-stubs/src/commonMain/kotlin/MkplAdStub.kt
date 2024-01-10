package ok.real.estate.advertisements.stubs

import ok.real.estate.advertisements.common.models.*

object MkplAdStub {
    fun get() = MkplAd(
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

    fun prepareResult(block: MkplAd.() -> Unit): MkplAd = get().apply(block)

    fun prepareSearchList(filter: String, status: MkplStatus) = listOf(
        mkplAdDemand("d-666-01", filter, status),
        mkplAdDemand("d-666-02", filter, status),
        mkplAdDemand("d-666-03", filter, status),
        mkplAdDemand("d-666-04", filter, status),
        mkplAdDemand("d-666-05", filter, status),
        mkplAdDemand("d-666-06", filter, status),
    )

    fun prepareOffersList(filter: String, status: MkplStatus) = listOf(
        mkplAdSupply("s-666-01", filter, status),
        mkplAdSupply("s-666-02", filter, status),
        mkplAdSupply("s-666-03", filter, status),
        mkplAdSupply("s-666-04", filter, status),
        mkplAdSupply("s-666-05", filter, status),
        mkplAdSupply("s-666-06", filter, status),
    )

    private fun mkplAdDemand(id: String, filter: String, type: MkplStatus) =
        mkplAd(get(), id = id, filter = filter, status = type)

    private fun mkplAdSupply(id: String, filter: String, type: MkplStatus) =
        mkplAd(get(), id = id, filter = filter, status = type)

    private fun mkplAd(base: MkplAd, id: String, filter: String, status: MkplStatus) = base.copy(
        id = MkplAdId(id),
        realEstateType = MkplRealEstateType.valueOf("$filter"),
        realEstateYear = "year $filter $id",
        realEstateArea = "area $filter $id",
        description = "desc $filter $id",
        adStatus = status,
    )

}
