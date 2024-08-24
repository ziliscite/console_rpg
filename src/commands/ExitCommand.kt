package commands

import config.Cache
import config.Config
import kotlin.system.exitProcess

class ExitCommand: Command {
    override val name: String
        get() = "exit"

    override val description: String
        get() = "exit the program"

    override fun execute(params: Array<String>, config: Config) {
        exitProcess(0)
    }
}