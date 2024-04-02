package kr.camp.sns.data

import java.io.Serializable

data class User(
    val id: String,
    val password: String,
    val name: String
) : Serializable {

    var description = ""
        private set

    var birthDate = "2000년 1월 1일"
        private set

    var mbti = "ENFP"
        private set

    private val _postings = mutableListOf<Posting>()
    val posting: List<Posting> get() = _postings

    fun addPosting(posting: Posting) {
        _postings.add(posting)
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setBirthDate(birthDate: String) {
        this.birthDate = birthDate
    }

    fun setMBTI(mbti: String) {
        this.mbti = mbti
    }
}