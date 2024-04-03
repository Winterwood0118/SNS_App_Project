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

        val currentPosting = intent.getSerializableExtra("") as Posting
        val currentUser = intent.getSerializableExtra("") as User

        initView(currentPosting, currentUser)
    }

    private fun initView(
        currentPosting: Posting,
        currentUser: User
    ) {
        binding.detailCurrentUserProfileTitleTextView.text = currentUser.name
        binding.detailCurrentContentImageView.setImageResource(currentPosting.imageDrawableId)
        binding.detailCurrentContentDescriptionTextView.text = currentPosting.description
        binding.detailCurrentContentNumLikeTextView.text = "${currentPosting.likeCount} 좋아요"

        binding.detailCurrentContentLikeImageView.run {
            setOnClickListener {
                currentPosting.switchLike(currentUser.id)
                if(currentPosting.isLiked(currentUser.id)) {
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
