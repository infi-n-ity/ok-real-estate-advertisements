package ok.real.estate.advertisements.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ok.real.estate.advertisements.common.helpers.asMkplError
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.repo.*

class RepoAdSQL(
    properties: SqlProperties,
    initObjects: Collection<MkplAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(AdTable)
            SchemaUtils.create(AdTable)
            initObjects.forEach { createAd(it) }
        }
    }

    private fun createAd(ad: MkplAd): MkplAd {
        val res = AdTable.insert {
            to(it, ad, randomUuid)
        }

        return AdTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbAdResponse): DbAdResponse =
        transactionWrapper(block) { DbAdResponse.error(it.asMkplError()) }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse = transactionWrapper {
        DbAdResponse.success(createAd(rq.ad))
    }

    private fun read(id: MkplAdId): DbAdResponse {
        val res = AdTable.select {
            AdTable.id eq id.asString()
        }.singleOrNull() ?: return DbAdResponse.errorNotFound
        return DbAdResponse.success(AdTable.from(res))
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: MkplAdId,
        lock: MkplAdLock,
        block: (MkplAd) -> DbAdResponse
    ): DbAdResponse =
        transactionWrapper {
            if (id == MkplAdId.NONE) return@transactionWrapper DbAdResponse.errorEmptyId

            val current = AdTable.select { AdTable.id eq id.asString() }
                .firstOrNull()
                ?.let { AdTable.from(it) }

            when {
                current == null -> DbAdResponse.errorNotFound
                current.lock != lock -> DbAdResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse =
        update(rq.ad.id, rq.ad.lock) {
            AdTable.update({
                (AdTable.id eq rq.ad.id.asString()) and (AdTable.lock eq rq.ad.lock.asString())
            }) {
                to(it, rq.ad, randomUuid)
            }
            read(rq.ad.id)
        }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse = update(rq.id, rq.lock) {
        AdTable.deleteWhere {
            (AdTable.id eq rq.id.asString()) and (AdTable.lock eq rq.lock.asString())
        }
        DbAdResponse.success(it)
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse =
        transactionWrapper({
            val res = AdTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != MkplUserId.NONE) {
                        add(AdTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.adStatus != MkplStatus.NONE) {
                        add(AdTable.adStatus eq rq.adStatus)
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (AdTable.realEstateYear like "%${rq.titleFilter}%")
                                or (AdTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbAdsResponse(data = res.map { AdTable.from(it) }, isSuccess = true)
        }, {
            DbAdsResponse.error(it.asMkplError())
        })
}
