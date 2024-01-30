package ok.real.estate.advertisements.backend.repo.sql

import ok.real.estate.advertisements.backend.repo.tests.RepoAdCreateTest
import ok.real.estate.advertisements.backend.repo.tests.RepoAdDeleteTest
import ok.real.estate.advertisements.backend.repo.tests.RepoAdReadTest
import ok.real.estate.advertisements.backend.repo.tests.RepoAdSearchTest
import ok.real.estate.advertisements.backend.repo.tests.RepoAdUpdateTest
import ok.real.estate.advertisements.common.repo.IAdRepository

class RepoAdSQLCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
