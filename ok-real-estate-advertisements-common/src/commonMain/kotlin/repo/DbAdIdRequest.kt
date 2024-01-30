package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplAd
import ok.real.estate.advertisements.common.models.MkplAdId
import ok.real.estate.advertisements.common.models.MkplAdLock

data class DbAdIdRequest(
    val id: MkplAdId,
    val lock: MkplAdLock = MkplAdLock.NONE,
) {
    constructor(ad: MkplAd): this(ad.id, ad.lock)
}
