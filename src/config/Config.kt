package config

import users.User

class Config(val cache: Cache, private var user: User?) {
    fun getUser(): User? {
        return this.user
    }

    fun setUser(user: User) {
        this.user = user
    }
}