package commands

import config.Config

interface Command {
    val name: String
    val description: String
    fun execute(params: Array<String>, config: Config)
}

interface APICommand: Command {
    val endpoint: String
}