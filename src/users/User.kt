package users

import serializers.Race

class User (private val race: Race) {
    fun displayRace() {
        race.display()
    }
}