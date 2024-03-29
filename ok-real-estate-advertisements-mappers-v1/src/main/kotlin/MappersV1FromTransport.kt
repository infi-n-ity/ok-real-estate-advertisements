package ok.real.estate.advertisements.mappers.v1

import ok.real.estate.advertisements.api.v1.models.*
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.stubs.MkplStubs
import ok.real.estate.advertisements.mappers.v1.exceptions.UnknownRequestClass

fun MkplContext.fromTransport(request: IRequest) = when (request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    is AdOffersRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { MkplAdId(it) } ?: MkplAdId.NONE
private fun String?.toAdWithId() = MkplAd(id = this.toAdId())
private fun String?.toAdLock() = this?.let { MkplAdLock(it) } ?: MkplAdLock.NONE
private fun IRequest?.requestId() = this?.requestId?.let { MkplRequestId(it) } ?: MkplRequestId.NONE

private fun AdDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    AdRequestDebugMode.PROD -> MkplWorkMode.PROD
    AdRequestDebugMode.TEST -> MkplWorkMode.TEST
    AdRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun AdDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    AdRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
    AdRequestDebugStubs.BAD_DESCRIPTION -> MkplStubs.BAD_DESCRIPTION
    AdRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
    AdRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: AdCreateRequest) {
    command = MkplCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: MkplAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: AdReadRequest) {
    command = MkplCommand.READ
    requestId = request.requestId()
    adRequest = request.ad.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdReadObject?.toInternal(): MkplAd = if (this != null) {
    MkplAd(id = id.toAdId())
} else {
    MkplAd.NONE
}

fun MkplContext.fromTransport(request: AdUpdateRequest) {
    command = MkplCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: MkplAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: AdDeleteRequest) {
    command = MkplCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdDeleteObject?.toInternal(): MkplAd = if (this != null) {
    MkplAd(
        id = id.toAdId(),
        lock = lock.toAdLock(),
    )
} else {
    MkplAd.NONE
}

fun MkplContext.fromTransport(request: AdSearchRequest) {
    command = MkplCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: AdOffersRequest) {
    command = MkplCommand.OFFERS
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdSearchFilter?.toInternal(): MkplAdFilter = MkplAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdCreateObject.toInternal(): MkplAd = MkplAd(
    realEstateType = this.realEstateType.fromTransport(),
    realEstateYear = this.realEstateYear ?: "",
    realEstateArea = this.realEstateArea ?: "",
    description = this.description ?: "",
    adStatus = this.adStatus.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun AdUpdateObject.toInternal(): MkplAd = MkplAd(
    id = this.id.toAdId(),
    realEstateType = this.realEstateType.fromTransport(),
    realEstateYear = this.realEstateYear ?: "",
    realEstateArea = this.realEstateArea ?: "",
    description = this.description ?: "",
    adStatus = this.adStatus.fromTransport(),
    visibility = this.visibility.fromTransport(),
    lock = lock.toAdLock(),
)

private fun RealEstateType?.fromTransport(): MkplRealEstateType = when (this) {
    RealEstateType.FLAT -> MkplRealEstateType.FLAT
    RealEstateType.HOUSE -> MkplRealEstateType.HOUSE
    RealEstateType.PLOT -> MkplRealEstateType.PLOT
    RealEstateType.COMMERCIAL -> MkplRealEstateType.COMMERCIAL
    RealEstateType.BUSINESS -> MkplRealEstateType.BUSINESS
    RealEstateType.INDUSTRIAL -> MkplRealEstateType.INDUSTRIAL
    RealEstateType.FACTORY -> MkplRealEstateType.FACTORY
    null -> MkplRealEstateType.NONE
}

private fun AdVisibility?.fromTransport(): MkplVisibility = when (this) {
    AdVisibility.PUBLIC -> MkplVisibility.VISIBLE_PUBLIC
    AdVisibility.OWNER_ONLY -> MkplVisibility.VISIBLE_TO_OWNER
    AdVisibility.REGISTERED_ONLY -> MkplVisibility.VISIBLE_TO_GROUP
    null -> MkplVisibility.NONE
}

private fun AdStatus?.fromTransport(): MkplStatus = when (this) {
    AdStatus.ACTIVE -> MkplStatus.ACTIVE
    AdStatus.SOLD -> MkplStatus.SOLD
    null -> MkplStatus.NONE
}

