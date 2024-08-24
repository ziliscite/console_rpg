package commands

import caching.Cache
import helper.CommandHandler
import helper.CommandSerializer
import serializers.*

class RaceCommand: APICommand {
    override val name: String
        get() = "race"

    override val description: String
        get() = "list all races or select a race"

    override val endpoint: String
        get() = "/api/races/"

    private val validSub = arrayOf("list", "detail")

    override fun execute(params: Array<String>, cache: Cache) {
        validateParams(params)

        val sub = params[0]
        var route = endpoint
        var callback: (String) -> Unit = this::displayAll

        if (sub == "detail") {
            route += params[1]
            callback = this::displayRace
        }

        cache.displayFromCache(route, callback)
        CommandHandler.handle(route, cache, callback)
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
        val race = CommandSerializer.displayResponse<Race>(response)

        println("Race: ${race.name}")
        println("URL: ${race.url}")
        println("Age: ${race.age}")
        println("Alignment: ${race.alignment}")
        println("Speed: ${race.speed}")

        println("Traits: ")
        for (trait in race.traits) {
            println("\tTrait: ${trait.name}")
        }

        println("Ability Bonuses: ")
        for (bonus in race.abilityBonuses) {
            println("\tBonus: ${bonus.bonus} ${bonus.abilityScore.name}")
        }

        println("Sub Races: ")
        for (sub in race.subRaces) {
            println("\tSub Race: ${sub.name}")
        }
    }

    private fun displayAll(response: String) {
        val races = CommandSerializer.displayResponse<Races>(response)

        println("Races: ")
        for (race in races.races) {
            println("\tRace: ${race.name}")
            println("\tURL: ${race.url}")
        }
    }
}