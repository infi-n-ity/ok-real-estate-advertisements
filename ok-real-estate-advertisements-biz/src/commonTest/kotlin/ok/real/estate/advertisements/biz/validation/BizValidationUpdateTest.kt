package ok.real.estate.advertisements.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.models.MkplCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = MkplCommand.UPDATE
    private val processor by lazy { MkplAdProcessor() }

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

