package ok.real.estate.advertisements.mappers.v1

import ok.real.estate.advertisements.api.v1.models.*
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.mappers.v1.exceptions.UnknownMkplCommand

fun MkplContext.toTransportAd(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.OFFERS -> toTransportOffers()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportCreate() = AdCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun MkplContext.toTransportRead() = AdReadResponse(
    responseType = "read",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun MkplContext.toTransportUpdate() = AdUpdateResponse(
    responseType = "update",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun MkplContext.toTransportDelete() = AdDeleteResponse(
    responseType = "delete",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransportAd()
)

fun MkplContext.toTransportSearch() = AdSearchResponse(
    responseType = "search",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun MkplContext.toTransportOffers() = AdOffersResponse(
    responseType = "offers",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == MkplState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransportAd()
)

fun List<MkplAd>.toTransportAd(): List<AdResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MkplContext.toTransportInit() = AdInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun MkplAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != MkplAdId.NONE }?.asString(),
    realEstateType = realEstateType.toTransportAd(),
    realEstateYear = realEstateYear.takeIf { it.isNotBlank() },
    realEstateArea = realEstateArea.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != MkplUserId.NONE }?.asString(),
    adStatus = adStatus.toTransportAd(),
    visibility = visibility.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
)

private fun Set<MkplAdPermissionClient>.toTransportAd(): Set<AdPermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MkplAdPermissionClient.toTransportAd() = when (this) {
    MkplAdPermissionClient.READ -> AdPermissions.READ
    MkplAdPermissionClient.UPDATE -> AdPermissions.UPDATE
    MkplAdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
    MkplAdPermissionClient.MAKE_VISIBLE_GROUP -> AdPermissions.MAKE_VISIBLE_GROUP
    MkplAdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
    MkplAdPermissionClient.DELETE -> AdPermissions.DELETE
}

private fun MkplRealEstateType.toTransportAd(): RealEstateType? = when (this) {
    MkplRealEstateType.FLAT -> RealEstateType.FLAT
    MkplRealEstateType.HOUSE -> RealEstateType.HOUSE
    MkplRealEstateType.PLOT -> RealEstateType.PLOT
    MkplRealEstateType.COMMERCIAL -> RealEstateType.COMMERCIAL
    MkplRealEstateType.BUSINESS -> RealEstateType.BUSINESS
    MkplRealEstateType.INDUSTRIAL -> RealEstateType.INDUSTRIAL
    MkplRealEstateType.FACTORY -> RealEstateType.FACTORY
    MkplRealEstateType.NONE -> null
}

private fun MkplVisibility.toTransportAd(): AdVisibility? = when (this) {
    MkplVisibility.VISIBLE_PUBLIC -> AdVisibility.PUBLIC
    MkplVisibility.VISIBLE_TO_GROUP -> AdVisibility.REGISTERED_ONLY
    MkplVisibility.VISIBLE_TO_OWNER -> AdVisibility.OWNER_ONLY
    MkplVisibility.NONE -> null
}

private fun MkplStatus.toTransportAd(): AdStatus? = when (this) {
    MkplStatus.ACTIVE -> AdStatus.ACTIVE
    MkplStatus.SOLD -> AdStatus.SOLD
    MkplStatus.NONE -> null
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
