package commands

import config.Config
import helpers.CommandHandler
import helpers.CommandSerializer
import serializers.Race
import users.User
import users.UserBuilder

class SetUserCommand: Command {
    private val builder: UserBuilder = UserBuilder()
    private var isBuilt: Boolean = false

    override val name: String
        get() = "set"

    override val description: String
        get() = "set certain traits or items to user if not already created"

    private val validSub = arrayOf("race", "class")

    override fun execute(params: Array<String>, config: Config) {
        if (config.getUser() != null) {
            throw Exception("user is already set")
        }
        // set race [race name] to set race
        validateParams(params)

        if (params[0] == "race") {
            val race = SetUserRace.getRace(params[1])
            setRace(race)
        }
    }

    private fun validateParams(params: Array<String>) {
        if (params.isEmpty()) {
            throw Exception("require parameters on set command: race or class")
        }

        if (!validSub.contains(params[0])) {
            throw Exception("invalid sub command: ${params[0]}")
        }

        if (params[0] == "race" && params.size <= 1) {
            throw Exception("sub command detail require a [race name]")
        }
    }

    private fun setRace(race: Race) {
        if (!builder.isRaceSet) {
            builder.race(race)
            return
        }

        print("do you want to re-set your race (${builder.getRace().name}) to ${race.name}? (yes/no): ")
        val args = readln()
        if (args == "no") {
            return
        }

        builder.race(race)
    }

    fun buildUser(): User {
        if (isBuilt) {
            throw Exception("user is already built")
        }

        if (!builder.isRaceSet) {
            throw Exception("race isn't set yet, not ready to be built")
        }

        return builder.build()
    }
}

object SetUserRace {
    private const val ENDPOINT = "/api/races/"

    fun getRace(name: String): Race {
        return resultHandler<Race>(ENDPOINT+name)
    }
}

inline fun <reified T>resultHandler(endpoint: String): T {
    val result = CommandHandler.getRequest(endpoint)
    var response: String? = null
    result.onSuccess {
        try {
            response = it
        } catch (e: Exception) {
            throw Exception("$${e.message}")
        }
    }.onFailure {
        throw Exception("${it.message}")
    }

    // It won't be null, please. Compiler, please trust me
    return CommandSerializer.getResponse<T>(response!!)
}