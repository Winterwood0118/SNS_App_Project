package kr.camp.sns.registry

import kr.camp.sns.data.User

class UserRegistry {
    companion object {
        private var userRegistry: UserRegistry? = null

        fun getInstance(): UserRegistry {
            return userRegistry ?: UserRegistry().apply { userRegistry = this }
        }
    }
    private val users = mutableListOf<User>()
    fun isUser(id: String): Boolean {
        return users.any { it.id == id }
    }
    fun addUser(user: User) {
        users.add(user)
    }
    fun findUserByIdAndPassword(id: String, password: String): User? {
        return users.find { it.id == id && it.password == password }
    }
}
