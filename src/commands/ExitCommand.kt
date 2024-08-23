package commands

import caching.Cache
import kotlin.system.exitProcess

class ExitCommand: Command {
    override val name: String
        get() = "exit"

    override val description: String
        get() = "exit the program"

    override fun execute(params: Array<String>, cache: Cache) {
        exitProcess(0)
    }
}