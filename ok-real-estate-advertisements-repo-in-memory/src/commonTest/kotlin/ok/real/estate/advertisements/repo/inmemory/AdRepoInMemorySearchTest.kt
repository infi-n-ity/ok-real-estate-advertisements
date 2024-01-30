package ok.real.estate.advertisements.repo.inmemory

import ok.real.estate.advertisements.backend.repo.tests.RepoAdSearchTest
import ok.real.estate.advertisements.common.repo.IAdRepository

class AdRepoInMemorySearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
