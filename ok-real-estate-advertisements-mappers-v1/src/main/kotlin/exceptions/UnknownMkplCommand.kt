package ok.real.estate.advertisements.mappers.v1.exceptions

import ok.real.estate.advertisements.common.models.MkplCommand

class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
