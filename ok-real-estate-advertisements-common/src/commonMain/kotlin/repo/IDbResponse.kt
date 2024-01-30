package ok.real.estate.advertisements.common.repo

import ok.real.estate.advertisements.common.models.MkplError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<MkplError>
}
