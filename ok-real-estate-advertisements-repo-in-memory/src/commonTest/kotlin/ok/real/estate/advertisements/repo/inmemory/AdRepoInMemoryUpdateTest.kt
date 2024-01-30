package ok.real.estate.advertisements.repo.inmemory

import ok.real.estate.advertisements.backend.repo.tests.RepoAdUpdateTest
import ok.real.estate.advertisements.common.repo.IAdRepository

class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
