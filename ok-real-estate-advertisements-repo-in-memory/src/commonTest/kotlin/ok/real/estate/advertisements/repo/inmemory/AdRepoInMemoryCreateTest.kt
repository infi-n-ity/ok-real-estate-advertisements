package ok.real.estate.advertisements.repo.inmemory

import ok.real.estate.advertisements.backend.repo.tests.RepoAdCreateTest

class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = AdRepoInMemory(
        initObjects = initObjects,
    )
}