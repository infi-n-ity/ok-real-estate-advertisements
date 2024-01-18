package ok.real.estate.advertisements.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.common.stubs.MkplStubs
import ok.real.estate.advertisements.stubs.MkplAdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdSearchStubTest {

    private val processor = MkplAdProcessor()
    val filter = MkplAdFilter(searchString = "2018")

    @Test
    fun read() = runTest {

        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.realEstateArea.contains(filter.searchString))
        assertTrue(first.realEstateYear.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (MkplAdStub.get()) {
            assertEquals(realEstateType, first.realEstateType)
            assertEquals(adStatus, first.adStatus)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_ID,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.DB_ERROR,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkplContext(
            command = MkplCommand.SEARCH,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.BAD_TITLE,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
