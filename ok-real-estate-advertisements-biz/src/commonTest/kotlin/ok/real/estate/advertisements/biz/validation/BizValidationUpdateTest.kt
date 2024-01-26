package ok.real.estate.advertisements.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ok.real.estate.advertisements.backend.repository.inmemory.AdRepoStub
import ok.real.estate.advertisements.biz.MkplAdProcessor
import ok.real.estate.advertisements.common.MkplCorSettings
import ok.real.estate.advertisements.common.models.MkplCommand
import validation.validationLockCorrect
import validation.validationLockEmpty
import validation.validationLockFormat
import validation.validationLockTrim
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = MkplCommand.UPDATE
    private val processor = MkplAdProcessor(MkplCorSettings(repoTest = AdRepoStub()))

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)


}

