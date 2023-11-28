package ok.real.estate.advertisements.stubs

import ok.real.estate.advertisements.common.models.MkplAd
import ok.real.estate.advertisements.common.models.MkplAdId
import ok.real.estate.advertisements.common.models.MkplRealEstateType
import ok.real.estate.advertisements.common.models.MkplStatus
import ok.real.estate.advertisements.stubs.MkplAdStubFlats.AD_ACTIVE_FLAT1
import ok.real.estate.advertisements.stubs.MkplAdStubFlats.AD_SOLD_FLAT1

object MkplAdStub {
    fun get(): MkplAd = AD_ACTIVE_FLAT1.copy()

    fun prepareResult(block: MkplAd.() -> Unit): MkplAd = get().apply(block)

    fun prepareSearchList(filter: String, status: MkplStatus) = listOf(
        mkplAdDemand("d-666-01", filter, status),
        mkplAdDemand("d-666-02", filter, status),
        mkplAdDemand("d-666-03", filter, status),
        mkplAdDemand("d-666-04", filter, status),
        mkplAdDemand("d-666-05", filter, status),
        mkplAdDemand("d-666-06", filter, status),
    )

    fun prepareOffersList(filter: String, status: MkplStatus) = listOf(
        mkplAdSupply("s-666-01", filter, status),
        mkplAdSupply("s-666-02", filter, status),
        mkplAdSupply("s-666-03", filter, status),
        mkplAdSupply("s-666-04", filter, status),
        mkplAdSupply("s-666-05", filter, status),
        mkplAdSupply("s-666-06", filter, status),
    )

    private fun mkplAdDemand(id: String, filter: String, type: MkplStatus) =
        mkplAd(AD_ACTIVE_FLAT1, id = id, filter = filter, status = type)

    private fun mkplAdSupply(id: String, filter: String, type: MkplStatus) =
        mkplAd(AD_SOLD_FLAT1, id = id, filter = filter, status = type)

    private fun mkplAd(base: MkplAd, id: String, filter: String, status: MkplStatus) = base.copy(
        id = MkplAdId(id),
        realEstateType = MkplRealEstateType.valueOf("$filter"),
        realEstateYear = "year $filter $id",
        realEstateArea = "area $filter $id",
        description = "desc $filter $id",
        adStatus = status,
    )

}
