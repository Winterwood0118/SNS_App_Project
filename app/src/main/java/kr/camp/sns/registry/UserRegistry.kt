package kr.camp.sns.registry

import kr.camp.sns.data.User

class UserRegistry {
    companion object {
        private var userRegistry: UserRegistry? = null

        fun getInstance(): UserRegistry { // 유저 레지스트리 사용할 때 인스턴스 만드는 함수
            return userRegistry ?: UserRegistry().apply { userRegistry = this }
        }
    }
    private val users = mutableListOf<User>()

    fun isUser(id: String): Boolean {
        return users.any { it.id == id } // id 중복검사 함수
    }
    fun addUser(user: User) {
        users.add(user) // 회원가입 성공 시 유저 정보를 List에 추가
    }
    fun findUserByIdAndPassword(id: String, password: String): User? {
        return users.find { it.id == id && it.password == password }  // 로그인 유효성 검사
    }
}
