package ok.real.estate.advertisements.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkplProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplProductId("")
    }
}
