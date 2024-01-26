package ok.real.estate.advertisements.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.*
import ok.real.estate.advertisements.stubs.MkplAdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MkplAdStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionCorrect(command: MkplCommand, processor: MkplAdProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = stub.id,
            realEstateType = MkplRealEstateType.FLAT,
            realEstateArea = "82",
            realEstateYear = "2018",
            description = "abc",
            adStatus = MkplStatus.ACTIVE,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            lock = MkplAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: MkplCommand, processor: MkplAdProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = stub.id,
            realEstateType = MkplRealEstateType.FLAT,
            realEstateArea = "82",
            realEstateYear = "2018",
            description = " \n\tabc \n\t",
            adStatus = MkplStatus.ACTIVE,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            lock = MkplAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: MkplCommand, processor: MkplAdProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = stub.id,
            realEstateType = MkplRealEstateType.FLAT,
            realEstateArea = "82",
            realEstateYear = "2018",
            description = "",
            adStatus = MkplStatus.ACTIVE,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            lock = MkplAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: MkplCommand, processor: MkplAdProcessor) = runTest {
    val ctx = MkplContext(
        command = command,
        state = MkplState.NONE,
        workMode = MkplWorkMode.TEST,
        adRequest = MkplAd(
            id = stub.id,
            realEstateType = MkplRealEstateType.FLAT,
            realEstateArea = "82",
            realEstateYear = "2018",
            description = "!@#$%^&*(),.{}",
            adStatus = MkplStatus.ACTIVE,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            lock = MkplAdLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
