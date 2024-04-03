package kr.camp.sns.registry

import kr.camp.sns.data.User

class UserRegistry {

    companion object {
        private var userRegistry: UserRegistry? = null

        fun getInstance(): UserRegistry {
            return userRegistry ?: UserRegistry().apply { userRegistry = this }
        }
    }

    private val users = mutableListOf<User>() // 앱이 실행되는 동안에만 저장되는 유저정보

    fun isUser(id: String): Boolean {
        return users.any { it.id == id } // id 중복검사 함수
    }

    fun addUser(user: User) {
        users.add(user) // 회원가입 성공 시 유저 정보를 List에 추가
    }

    fun findUserByIdAndPassword(id: String, password: String): User? {
        return users.find { it.id == id && it.password == password }
    }
}