package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplAd
import ok.real.estate.advertisements.common.models.MkplAdId

data class DbAdIdRequest(
    val id: MkplAdId,
) {
    constructor(ad: MkplAd): this(ad.id)
}
