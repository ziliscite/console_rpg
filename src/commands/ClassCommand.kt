package commands

import config.Cache
import config.Config

class ClassCommand: APICommand {
    override val name: String
        get() = "class"

    override val description: String
        get() = "list all classes or select a class to display"

    override val endpoint: String
        get() = "/api/classes/"

    override fun execute(params: Array<String>, config: Config) {
        TODO("Not yet implemented")
    }
}