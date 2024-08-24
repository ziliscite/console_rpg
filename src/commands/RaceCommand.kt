package commands

import config.Config
import helpers.CommandHandler
import helpers.CommandSerializer
import serializers.*

class RaceCommand: APICommand {
    override val name: String
        get() = "race"

    override val description: String
        get() = "list all races or select a race to display"

    override val endpoint: String
        get() = "/api/races/"

    private val validSub = arrayOf("list", "detail")

    override fun execute(params: Array<String>, config: Config) {
        validateParams(params)

        val sub = params[0]
        var route = endpoint
        var callback: (String) -> Unit = this::displayAll

        if (sub == "detail") {
            route += params[1]
            callback = this::displayRace
        }

        if (config.cache.displayFromCache(route, callback)) {
            return
        }

        CommandHandler.handle(route, config.cache, callback)
    }

    private fun validateParams(params: Array<String>) {
        if (params.isEmpty()) {
            throw Exception("require parameters on race command (list or detail [race name])")
        }

        if (!validSub.contains(params[0])) {
            throw Exception("invalid sub command: ${params[0]}")
        }

        if (params[0] == "detail" && params.size <= 1) {
            throw Exception("sub command detail require a [race name]")
        }
    }

    private fun displayRace(response: String) {
        val race = CommandSerializer.getResponse<Race>(response)
        race.display()
    }

    private fun displayAll(response: String) {
        val races = CommandSerializer.getResponse<Races>(response)

        println("Races: ")
        for (race in races.races) {
            println("\t- Race: ${race.name}")
        }
    }
}