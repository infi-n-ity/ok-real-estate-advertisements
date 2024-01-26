package ok.real.estate.advertisements.backend.repository.inmemory

import ok.real.estate.advertisements.common.models.MkplStatus
import ok.real.estate.advertisements.common.repo.*
import ok.real.estate.advertisements.stubs.MkplAdStub

class AdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return DbAdsResponse(
            data = MkplAdStub.prepareSearchList(filter = "", MkplStatus.SOLD),
            isSuccess = true,
        )
    }
}
