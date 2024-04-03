package kr.camp.sns
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityDetailBinding
class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val descriptionMaxLineNum = 2
    private var isFolded = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            val currentPosting: Posting = intent.getSerializableExtra("extra_post") as Posting
            val currentUser: String = intent.getStringExtra("extra_posting_user_name")!!
            initView(currentPosting, currentUser)
        } catch(e: Exception) {
            finish()
            // 게시물 열람 불가
        }
    }

    private fun initView(
        currentPosting: Posting,
        currentUserName: String
    ) {
        binding.detailCurrentUserProfileTitleTextView.text = currentUserName
        binding.detailCurrentContentImageView.setImageResource(currentPosting.imageDrawableId)
        binding.detailCurrentContentDescriptionTextView.text = currentPosting.description
        binding.detailCurrentContentNumLikeTextView.text = "${currentPosting.likeCount} 좋아요"

        binding.detailCurrentContentLikeImageView.run {
            setOnClickListener {
                currentPosting.switchLike(currentUserName)
                if(currentPosting.isLiked(currentUserName)) {
                    setImageResource(R.drawable.like_heart_icon)
                }else setImageResource(R.drawable.like_icon)
            }
        }

        binding.detailCurrentContentDescriptionTextView.run {
            setOnClickListener {
                maxLines = if(isFolded) Integer.MAX_VALUE else descriptionMaxLineNum
                isFolded = !isFolded
            }
        }
    }
}
