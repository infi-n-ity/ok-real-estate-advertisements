package docker

import ru.otus.otuskotlin.real.estate.advertisements.blackbox.docker.AbstractDockerCompose

object SpringDockerCompose : AbstractDockerCompose(
    "app-spring_1", 8080, "spring/docker-compose-spring.yml"
)