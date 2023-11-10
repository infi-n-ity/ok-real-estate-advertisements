package ok.real.estate.advertisements.common.models

data class MkplAdFilter(
    var searchString: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
    var adStatus: MkplStatus = MkplStatus.NONE,
)
