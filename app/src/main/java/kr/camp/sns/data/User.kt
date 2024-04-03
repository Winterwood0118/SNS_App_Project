package kr.camp.sns.data

import java.io.Serializable

data class User(
    val id: String,
    val password: String,
    val name: String
) : Serializable {

    var birthDate = "2000년 1월 1일"
        private set

    var mbti = "ENFP"
        private set

    private val _postings = mutableListOf<Posting>()
    val postings: List<Posting> get() = _postings

    fun addPostings(vararg posting: Posting) {
        _postings.addAll(posting)
    }

    fun setBirthDate(birthDate: String) {
        this.birthDate = birthDate
    }

    fun setMBTI(mbti: String) {
        this.mbti = mbti
    }
}