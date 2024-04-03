package kr.camp.sns.data

import java.io.Serializable

data class User(
    val id: String,
    val password: String,
    val name: String
) : Serializable {

    var profileDrawableId = -1
        private set

    var mbti = "ENFP"
        private set

    var description = ""
        private set

    private val _postings = mutableListOf<Posting>()
    val postings: List<Posting> get() = _postings

    fun addPostings(vararg posting: Posting) {
        _postings.addAll(posting)
    }

    fun setProfileDrawableId(profileDrawableId: Int) {
        this.profileDrawableId = profileDrawableId
    }

    fun setMBTI(mbti: String) {
        this.mbti = mbti
    }

    fun setDescription(description: String) {
        this.description = description
    }
}