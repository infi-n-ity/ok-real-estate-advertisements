package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplAd
import ok.real.estate.advertisements.common.models.MkplError

data class DbAdsResponse(
    override val data: List<MkplAd>?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList(),
): IDbResponse<List<MkplAd>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdsResponse(emptyList(), true)
        fun success(result: List<MkplAd>) = DbAdsResponse(result, true)
        fun error(errors: List<MkplError>) = DbAdsResponse(null, false, errors)
        fun error(error: MkplError) = DbAdsResponse(null, false, listOf(error))
    }
}
