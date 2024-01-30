package ok.real.estate.advertisements.common

import ok.real.estate.advertisements.common.repo.IAdRepository

data class MkplCorSettings(
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = MkplCorSettings()
    }
}
