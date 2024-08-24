package users

import serializers.Race

class UserBuilder {
    private var race = Race(null, null, null, null, null, null, null, null)
    var isRaceSet = false

    fun race(race: Race): UserBuilder {
        this.race = race
        isRaceSet = true

        return this
    }

    fun getRace(): Race {
        return this.race
    }

    fun build(): User {
        return User(race)
    }
}