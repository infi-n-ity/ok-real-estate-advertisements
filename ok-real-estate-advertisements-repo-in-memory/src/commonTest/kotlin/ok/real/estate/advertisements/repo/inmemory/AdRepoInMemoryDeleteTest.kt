package ok.real.estate.advertisements.repo.inmemory

import ok.real.estate.advertisements.backend.repo.tests.RepoAdDeleteTest
import ok.real.estate.advertisements.common.repo.IAdRepository

class AdRepoInMemoryDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
