package kr.camp.sns

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.activity.MyPageActivity
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.intent.IntentKey
import kr.camp.sns.databinding.ActivityMainBinding
import kr.camp.sns.registry.UserRegistry


class MainActivity : AppCompatActivity() {
    private val userRegistry = UserRegistry.getInstance()
    private var user: User? = null
    private var isLogin = false
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        makeRandomUserPost()

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    isLogin = result.data?.getBooleanExtra(IntentKey.LOGIN, false) ?: false
                    user = result.data?.getSerializableExtra(IntentKey.USER) as User
                }
            }

        binding.mainLoginImageView.setOnClickListener {
            if (isLogin) {
                val intent = Intent(this, MyPageActivity::class.java)
                intent.putExtra(IntentKey.USER, user)
                startActivity(intent)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startForResult.launch(intent)
            }
        }
        for (i in userList.indices) {
            userList[i].apply {
                customNameTextView.text = defaultUser[i].name
                customUserProfileImageView.setImageResource(defaultUser[i].profileDrawableId)
            }
        }

        for (i in postList.indices) {
            postList[i].apply {
                val user = userRegistry.users.random()
                val post = user.postings.random()
                val countOfLike = "${post.likeCount} likes"
                val postString =
                    SpannableStringBuilder("${user.name} ${post.description}")
                postString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    user.name.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                customPostHeartImageView.apply {
                    setOnClickListener {
                        if (isLogin) {
                            post.switchLike(user.name)
                            if (post.isLiked(user.name)) {
                                setImageResource(R.drawable.like_heart_icon)
                            } else setImageResource(R.drawable.like_icon)
                        }
                    }

                }
                customPostProfileImageView.setImageResource(user.profileDrawableId)
                customPostCountOfLikeTextView.text = countOfLike
                customPostNameTextView.text = user.name
                customPostMainTextView.text = postString
                customPostMainImageView.apply {
                    setImageResource(post.imageDrawableId)
                    setOnClickListener {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.apply {
                            putExtra(IntentKey.USER, user)
                            putExtra(IntentKey.POST, post)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
    private fun makeRandomUserPost(){
        //디폴트 유저, 랜덤 포스트 생성
        repeat(50) {
            randomPosting.add(Posting(postImageId.random(), getString(postTextId.random())))
        }
        defaultUser.forEach { userRegistry.addUser(it) }
        userRegistry.users.forEach {
            it.addPostings(randomPosting.random())
            it.addPostings(randomPosting.random())
            it.addPostings(randomPosting.random())
            it.setProfileDrawableId(postImageId.random())
        }
    }

    private val userList by lazy {
        mutableListOf(
            binding.mainCustomUserItem1,
            binding.mainCustomUserItem2,
            binding.mainCustomUserItem3,
            binding.mainCustomUserItem4,
            binding.mainCustomUserItem5
        )
    }

    private val postList by lazy {
        mutableListOf(
            binding.mainPost1,
            binding.mainPost2,
            binding.mainPost3,
            binding.mainPost4,
            binding.mainPost5
        )
    }

    private val defaultUser = arrayOf(
        User("default_user_id1", "test", "default_name1"),
        User("default_user_id2", "test", "default_name2"),
        User("default_user_id3", "test", "default_name3"),
        User("default_user_id4", "test", "default_name4"),
        User("default_user_id5", "test", "default_name5")
    )

    private val postImageId = arrayOf(
        R.drawable.golden_state_warriors,
        R.drawable.dog,
        R.drawable.james_harden,
        R.drawable.stephen_curry,
        R.drawable.klay_thompson,
        R.drawable.kevin_durant,
        R.drawable.lebron_james,
        R.drawable.img_donut_glazeddonut,
        R.drawable.img_test
    )

    private val postTextId by lazy {
        arrayOf(
            R.string.text_test1,
            R.string.text_test2,
            R.string.text_test3,
            R.string.text_test4,
            R.string.text_test5
        )
    }

    private val randomPosting = mutableListOf<Posting>()

}