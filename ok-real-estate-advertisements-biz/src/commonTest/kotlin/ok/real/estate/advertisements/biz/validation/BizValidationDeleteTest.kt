package ok.real.estate.advertisements.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.models.MkplCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = MkplCommand.DELETE
    private val processor by lazy { MkplAdProcessor() }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

