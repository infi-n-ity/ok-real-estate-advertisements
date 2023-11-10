package ok.real.estate.advertisements.common.helpers

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplError

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkplError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun MkplContext.addError(vararg error: MkplError) = errors.addAll(error)
