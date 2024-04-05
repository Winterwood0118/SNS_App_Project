package kr.camp.sns
import android.content.Intent
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.activity.MyPageActivity
import kr.camp.sns.intent.IntentKey
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private var loginInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            val currentPosting: Posting = intent
                .getSerializableExtra(IntentKey.POST) as Posting
            val currentUser: User = intent
                .getSerializableExtra(IntentKey.USER)  as User
            loginInfo = intent
                .getBooleanExtra(IntentKey.LOGIN, false)

            initView(currentPosting, currentUser)
        } catch (e: Exception) {
            finish()
            // 게시물 열람 불가
        }
    }

    private fun initView(
        currentPosting: Posting,
        currentUser: User
    ) {
        val contentDescriptionText = "${currentUser.name} ${currentPosting.description}"
        val span = SpannableStringBuilder(contentDescriptionText).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                currentUser.name.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }

        binding.detailCurrentUserProfileTitleTextView.text = currentUser.name
        binding.detailCurrentContentImageView.setImageResource(currentPosting.imageDrawableId)
        binding.detailCurrentContentDescriptionTextView.text = span

        binding.detailCurrentUserProfileImageView.setImageResource(currentUser.profileDrawableId)
        setNumLikeText(currentPosting.likeCount)

        binding.detailCurrentUserProfileImageView.run {
            setOnClickListener {
                startActivity(
                    Intent(this@DetailActivity, MyPageActivity::class.java)
                        .apply {
                            putExtra(IntentKey.USER, currentUser)
                            putExtra(IntentKey.LOGIN, loginInfo)
                        }
                )
            }
        }

//        binding.detailCurrentContentLikeImageView.run {
//            setOnClickListener {
//                currentPosting.switchLike(currentUser.name)
//                if(currentPosting.isLiked(currentUser.name)) {
//                    setImageResource(R.drawable.like_heart_icon)
//                }else setImageResource(R.drawable.like_icon)
//                setNumLikeText(currentPosting.likeCount)
//            }
//        }

        binding.detailCurrentContentLikeImageView.setOnClickListener {
            if(!currentPosting.isLiked(currentUser.name)) {
                currentPosting.switchLike(currentUser.name)
                binding.detailCurrentContentLikeImageView.visibility = View.GONE
                binding.detailCurrentContentLikeHeartImageView.visibility = View.VISIBLE
                setNumLikeText(currentPosting.likeCount)
            }
        }

        binding.detailCurrentContentLikeHeartImageView.setOnClickListener {
            if(currentPosting.isLiked(currentUser.name)) {
                currentPosting.switchLike(currentUser.name)
                binding.detailCurrentContentLikeImageView.visibility = View.VISIBLE
                binding.detailCurrentContentLikeHeartImageView.visibility = View.GONE
                setNumLikeText(currentPosting.likeCount)
            }
        }

        binding.detailCurrentContentDescriptionTextView.run {
            setOnClickListener {
                if(maxLines < Int.MAX_VALUE) {
                    maxLines = Int.MAX_VALUE
                    text = span
                }else maxLines = numMaxLines
            }
        }
    }

    private fun setNumLikeText(count: Int) {
        binding.detailCurrentContentNumLikeTextView.text = "$count 좋아요"
    }
}
