package commands

class Commands {
    private val commands = mutableListOf<Command>()
    fun addCommand(command: Command) {
        commands.add(command)
    }

    fun getCommand(name: String) : Result<Command> {
        val result = commands.find { it.name == name }
        return if (result != null) {
            Result.success(result)
        } else {
            Result.failure(IllegalArgumentException("Command $name not found"))
        }
    }
}