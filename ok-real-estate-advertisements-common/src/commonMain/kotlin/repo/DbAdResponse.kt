package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplAd
import ok.real.estate.advertisements.common.models.MkplError

data class DbAdResponse(
    override val data: MkplAd?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList()
): IDbResponse<MkplAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: MkplAd) = DbAdResponse(result, true)
        fun error(errors: List<MkplError>) = DbAdResponse(null, false, errors)
        fun error(error: MkplError) = DbAdResponse(null, false, listOf(error))
    }
}
