package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test

import fixture.client.RestClient
import io.kotest.core.annotation.Ignored
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.docker.SpringDockerCompose
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.BaseFunSpec
import ru.otus.otuskotlin.real.estate.advertisements.blackbox.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client)
})
class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
// TODO class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
