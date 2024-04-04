package kr.camp.sns
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.activity.MyPageActivity
import kr.camp.sns.intent.IntentKey
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
            val currentPosting: Posting = intent
                .getSerializableExtra(IntentKey.POST) as Posting
            val currentUser: User = intent
                .getSerializableExtra(IntentKey.USER)  as User
            initView(currentPosting, currentUser)
//            initView(
//                Posting(
//                    R.drawable.dog,
//                    resources.getString(R.string.text_test1)
//                ),
//                User("abcd", "1234", "tom").apply {
//                    setProfileDrawableId(R.drawable.golden_state_warriors)
//                }
//            )
        } catch(e: Exception) {
            val currentPosting: Posting = intent.getSerializableExtra(IntentKey.POST) as Posting
            val currentUser: String = intent.getStringExtra(IntentKey.POSTING_USER_NAME)!!
            initView(currentPosting, User(id = "", password = "", currentUser))
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

        initViewClickListener(currentPosting, currentUser)
    }

    private fun initViewClickListener(
        currentPosting: Posting,
        currentUser: User
    ) {
        binding.detailCurrentUserProfileImageView.run {
            setOnClickListener {
                startActivity(
                    Intent(this@DetailActivity, MyPageActivity::class.java)
                        .apply { putExtra(IntentKey.USER, currentUser) }
                )
            }
        }

        binding.detailCurrentContentLikeImageView.run {
            setOnClickListener {
                currentPosting.switchLike(currentUser.name)
                if(currentPosting.isLiked(currentUser.name)) {
                    setImageResource(R.drawable.like_heart_icon)
                }else setImageResource(R.drawable.like_icon)
                setNumLikeText(currentPosting.likeCount)
            }
        }

        binding.detailCurrentContentDescriptionTextView.run {
            setOnClickListener {
                maxLines = if (isFolded) Integer.MAX_VALUE else descriptionMaxLineNum
                isFolded = !isFolded
            }
        }
    }

    private fun setNumLikeText(count: Int) {
        binding.detailCurrentContentNumLikeTextView.text = "$count 좋아요"
    }
}
