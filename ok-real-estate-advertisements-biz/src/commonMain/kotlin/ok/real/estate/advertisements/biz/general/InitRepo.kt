package ok.real.estate.advertisements.biz.general

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.helpers.errorAdministration
import ok.real.estate.advertisements.common.helpers.fail
import ok.real.estate.advertisements.common.models.MkplWorkMode
import ok.real.estate.advertisements.common.repo.IAdRepository
import ok.real.estate.advertisements.cor.ICorChainDsl
import ok.real.estate.advertisements.cor.worker

fun ICorChainDsl<MkplContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when {
            workMode == MkplWorkMode.TEST -> settings.repoTest
            workMode == MkplWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != MkplWorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
