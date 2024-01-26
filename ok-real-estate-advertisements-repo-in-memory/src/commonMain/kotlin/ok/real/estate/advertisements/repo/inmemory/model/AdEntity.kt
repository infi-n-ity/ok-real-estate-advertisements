package ok.real.estate.advertisements.backend.repository.inmemory.model

import ok.real.estate.advertisements.common.models.*

data class AdEntity(
    val id: String? = null,
    val realEstateType: String? = null,
    val realEstateYear: String? = null,
    val realEstateArea: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adStatus: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: MkplAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        realEstateType = model.realEstateType.takeIf { it != MkplRealEstateType.NONE }?.name,
        realEstateYear = model.realEstateYear.takeIf { it.isNotBlank() },
        realEstateArea = model.realEstateArea.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        adStatus = model.adStatus.takeIf { it != MkplStatus.NONE }?.name,
        visibility = model.visibility.takeIf { it != MkplVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = MkplAd(
        id = id?.let { MkplAdId(it) }?: MkplAdId.NONE,
        realEstateType = realEstateType?.let { MkplRealEstateType.valueOf(it) }?: MkplRealEstateType.NONE,
        realEstateYear = realEstateYear?: "",
        realEstateArea = realEstateArea?: "",
        description = description?: "",
        ownerId = ownerId?.let { MkplUserId(it) }?: MkplUserId.NONE,
        adStatus = adStatus?.let { MkplStatus.valueOf(it) }?: MkplStatus.NONE,
        visibility = visibility?.let { MkplVisibility.valueOf(it) }?: MkplVisibility.NONE,
        lock = lock?.let { MkplAdLock(it) } ?: MkplAdLock.NONE,
    )
}
