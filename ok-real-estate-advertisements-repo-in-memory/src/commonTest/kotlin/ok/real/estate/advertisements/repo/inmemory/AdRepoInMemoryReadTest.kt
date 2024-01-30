package ok.real.estate.advertisements.repo.inmemory

import ok.real.estate.advertisements.backend.repo.tests.RepoAdReadTest
import ok.real.estate.advertisements.common.repo.IAdRepository

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
