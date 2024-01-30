package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplStatus
import ok.real.estate.advertisements.common.models.MkplUserId

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: MkplUserId = MkplUserId.NONE,
    val adStatus: MkplStatus = MkplStatus.NONE,
)
