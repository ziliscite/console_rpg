import config.Cache
import commands.*
import config.Config
import java.util.Locale

/*
* REPL project involving DnD api from https://www.dnd5eapi.co/
* */

const val SERVER = "https://www.dnd5eapi.co"

fun instantiate(allCommands: Commands) {
    allCommands.addCommand(ExitCommand())
    allCommands.addCommand(RaceCommand())
    allCommands.addCommand(SetUserCommand())
}

fun main() {
    val allCommands = Commands()
    instantiate(allCommands)

    val cache = Cache()
    val config = Config(cache, null)

    while (true) {
        print("console > ")
        val args = readln()
        val command = parseArgs(args)

        val result = allCommands.getCommand(command.first)
        result.onSuccess {
            try {
                it.execute(command.second, config)
            } catch (e: Exception) {
                println("${command.first}: ${e.message}")
            }
        }.onFailure {
            println("${it.message}")
        }
    }
}

fun parseArgs(args: String): Pair<String, Array<String>> {
    val params = args.lowercase(Locale.getDefault()).split(" ").toTypedArray()
    val command = params[0]
    return Pair(command, params.drop(1).toTypedArray())
}