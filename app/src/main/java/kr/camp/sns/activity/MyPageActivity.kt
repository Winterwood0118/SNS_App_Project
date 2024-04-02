package kr.camp.sns.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.R
import kr.camp.sns.activity.adapter.MyPagePostingAdapter
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

    private lateinit var user: User

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra("User") as? User ?: User(
            "rhdwlgns0725",
            "qlalfqjsgh",
            "공지훈"
        ).apply {
            setDescription("테스트 설명입니다!")
            setBirthDate("2004년 07월 25일")
            setMBTI("ESTJ")
            addPostings(
                Posting(R.drawable.stephen_curry, "스테픈 커리"),
                Posting(R.drawable.golden_state_warriors, "골든스테이트 워리어스 우승"),
                Posting(R.drawable.lebron_james, "르브론 제임스"),
                Posting(R.drawable.klay_thompson, "클레이 탐슨은 드레이먼드 그린보다 3점을 못던지는 선수입니다."),
                Posting(R.drawable.kevin_durant, "케빈 듀란트"),
                Posting(R.drawable.james_harden, "제임스 하든")
            )
        }

        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        idTextView.text = user.id
        nameTextView.text = user.name
        mbtiTextView.text = user.mbti
        descriptionTextView.text = user.description

        nameEditButton.editButton.text = "이름 편집"
        mbtiEditButton.editButton.text = "MBTI 편집"
        descriptionEditButton.editButton.text = "소개 편집"

        postingCountLayout.apply {
            countTextView.text = user.postings.size.toString()
            titleTextView.text = "게시물"
        }
        followerCountLayout.apply {
            countTextView.text = (100..300).random().toString()
            titleTextView.text = "팔로워"
        }
        followingCountLayout.apply {
            countTextView.text = (100..300).random().toString()
            titleTextView.text = "팔로잉"
        }
        postingListView.apply {
            val myPagePostingAdapter = MyPagePostingAdapter(this@MyPageActivity, user.postings)
            adapter = myPagePostingAdapter
            setOnItemClickListener { _, _, position, _ ->
                val posting = myPagePostingAdapter.getItem(position)
                showPostingPopup(posting)
            }
        }
    }

    private fun showPostingPopup(posting: Posting) {
        val intent = Intent(this, MyPagePostingPopupActivity::class.java).apply {
            putExtra("Posting", posting)
        }
        startActivity(intent)
    }

    fun onMyPageBackspaceButtonClick(view: View) {
        finish()
    }

    fun onTitleEditButtonClick(view: View) {

    }

    fun onMBTIEditButtonClick(view: View) {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        builder.setView(input)
        builder.show()
    }

    fun onDescriptionEditButtonClick(view: View) {

    }
}