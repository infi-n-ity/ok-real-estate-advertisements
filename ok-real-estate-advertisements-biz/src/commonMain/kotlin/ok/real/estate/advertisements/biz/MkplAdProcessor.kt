package ok.real.estate.advertisements.biz

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.stubs.MkplAdStub

class MkplAdProcessor {
    suspend fun exec(ctx: MkplContext) {
        ctx.adResponse = MkplAdStub.get()
    }
}
