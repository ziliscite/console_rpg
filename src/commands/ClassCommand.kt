package commands

import caching.Cache

class ClassCommand: APICommand {
    override val name: String
        get() = "class"

    override val description: String
        get() = "list all classes or select a class to display"

    override val endpoint: String
        get() = "/api/classes/"

    override fun execute(params: Array<String>, cache: Cache) {
        TODO("Not yet implemented")
    }
}