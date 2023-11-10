package ok.real.estate.advertisements.common.helpers

import ok.real.estate.advertisements.common.MkplContext
import ok.real.estate.advertisements.common.models.MkplCommand

fun MkplContext.isUpdatableCommand() =
    this.command in listOf(MkplCommand.CREATE, MkplCommand.UPDATE, MkplCommand.DELETE)
