package ok.real.estate.advertisements.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ok.real.estate.advertisements.common.models.*

object AdTable : Table("ad") {
    val id = varchar("id", 128)
    val realEstateType = enumeration("real_estate_type", MkplRealEstateType::class)
    val realEstateYear = varchar("real_estate_year", 512)
    val realEstateArea = varchar("real_estate_area", 512)
    val description = varchar("description", 512)
    val owner = varchar("owner", 128)
    val visibility = enumeration("visibility", MkplVisibility::class)
    val adStatus = enumeration("ad_status", MkplStatus::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = MkplAd(
        id = MkplAdId(res[id].toString()),
        realEstateType = res[realEstateType],
        realEstateYear = res[realEstateYear],
        realEstateArea = res[realEstateArea],
        description = res[description],
        ownerId = MkplUserId(res[owner].toString()),
        visibility = res[visibility],
        adStatus = res[adStatus],
        lock = MkplAdLock(res[lock])
    )

    fun from(res: ResultRow) = MkplAd(
        id = MkplAdId(res[id].toString()),
        realEstateType = res[realEstateType],
        realEstateYear = res[realEstateYear],
        realEstateArea = res[realEstateArea],
        description = res[description],
        ownerId = MkplUserId(res[owner].toString()),
        visibility = res[visibility],
        adStatus = res[adStatus],
        lock = MkplAdLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, ad: MkplAd, randomUuid: () -> String) {
        it[id] = ad.id.takeIf { it != MkplAdId.NONE }?.asString() ?: randomUuid()
        it[realEstateType] = ad.realEstateType
        it[realEstateYear] = ad.realEstateYear
        it[realEstateArea] = ad.realEstateArea
        it[description] = ad.description
        it[owner] = ad.ownerId.asString()
        it[visibility] = ad.visibility
        it[adStatus] = ad.adStatus
        it[lock] = ad.lock.takeIf { it != MkplAdLock.NONE }?.asString() ?: randomUuid()
    }

}
