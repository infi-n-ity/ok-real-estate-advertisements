package ok.real.estate.advertisements.common.models

data class MkplAd(
    var id: MkplAdId = MkplAdId.NONE,
    var realEstateType: MkplRealEstateType = MkplRealEstateType.NONE,
    var realEstateYear: String = "",
    var realEstateArea: String = "",
    var description: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
    var adStatus: MkplStatus = MkplStatus.NONE,
    var visibility: MkplVisibility = MkplVisibility.NONE,
    var productId: MkplProductId = MkplProductId.NONE,
    var lock: MkplAdLock = MkplAdLock.NONE,
    val permissionsClient: MutableSet<MkplAdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): MkplAd = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = MkplAd()
    }
}
