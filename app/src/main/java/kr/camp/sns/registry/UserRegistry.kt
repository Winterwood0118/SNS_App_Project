package kr.camp.sns.registry

import kr.camp.sns.R
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User

class UserRegistry {

    companion object {
        private var userRegistry: UserRegistry? = null

        fun getInstance(): UserRegistry { // 유저 레지스트리 사용할 때 인스턴스 만드는 함수
            return userRegistry ?: UserRegistry().apply { userRegistry = this }
        }
    }

    private val _users = mutableListOf<User>(
        User("rhdwlgns0725", "qlalfqjsgh1*", "공지훈").apply {
            setProfileDrawableId(R.drawable.stephen_curry)
            setMBTI("ESTJ")
            setDescription("항상 열심히!")
            addPostings(
                Posting(R.drawable.golden_state_warriors, "골든스테이트 워리어스 우승"),
                Posting(R.drawable.lebron_james, "르브론 제임스입니다!")
            )
        },
        User("wjdwnsgh789", "wjdwnsgh758@", "정준호").apply {
            setProfileDrawableId(R.drawable.stephen_curry)
            setMBTI("INFP")
            setDescription("1인분만")
            addPostings(
                Posting(R.drawable.logo_1, "2024한화 우승"),
                Posting(R.drawable.ruy_1, "류현진 복귀")
            )
        },
        User("dltjdals125", "random123!", "이성민").apply {
            setProfileDrawableId(R.drawable.img_test)
            setMBTI("INFP")
            setDescription("오늘은 신나는 날이에요")
            addPostings(
                Posting(R.drawable.img_pla, "심신안정에는 프라모델"),
                Posting(R.drawable.img_cat, "길가다가 본 고양이")
            )
        }
    )
    
    val users: List<User> = _users

    fun isUser(id: String): Boolean {
        return _users.any { it.id == id } // id 중복검사 함수
    }

    fun addUser(user: User) {
        _users.add(user) // 회원가입 성공 시 유저 정보를 List에 추가
    }

    fun findUserByIdAndPassword(id: String, password: String): User? {
        return _users.find { it.id == id && it.password == password }  // 로그인 유효성 검사
    }
}
