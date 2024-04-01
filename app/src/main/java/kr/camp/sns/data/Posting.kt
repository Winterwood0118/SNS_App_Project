package kr.camp.sns.data

import java.io.Serializable

data class Posting(
    val imageDrawableId: Int,
    val description: String
) : Serializable {

    private val likes = mutableSetOf<String>()

    val likeCount get() = likes.size

    fun isLiked(id: String): Boolean = likes.contains(id)

    fun switchLike(id: String) {
        if (isLiked(id)) {
            likes.remove(id)
        } else {
            likes.add(id)
        }
    }
}