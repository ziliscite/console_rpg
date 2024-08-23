package commands

import caching.Cache

interface Command {
    val name: String
    val description: String
    fun execute(params: Array<String>, cache: Cache)
}

interface APICommand: Command {
    val endpoint: String
}