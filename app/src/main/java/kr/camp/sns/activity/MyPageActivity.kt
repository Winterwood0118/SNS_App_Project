package kr.camp.sns.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.R
import kr.camp.sns.activity.adapter.MyPagePostingAdapter
import kr.camp.sns.activity.intent.IntentKey
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private companion object {
        const val NAME_EDIT = "이름 편집"
        const val MBTI_EDIT = "MBTI 편집"
        const val DESCRIPTION_EDIT = "소개 편집"
        const val POSTING = "게시물"
        const val FOLLOWER = "팔로워"
        const val FOLLOWING = "팔로잉"
        const val CONFIRM = "확인"
        const val CANCEL = "취소"

        private val followCountRange = (100..300)

        fun getFollow(): Int = followCountRange.random()

        val emptyUser = User(
            "developher",
            "developher",
            "개발자"
        ).apply {
            setProfileDrawableId(R.drawable.stephen_curry)
            setDescription("테스트 개발자입니다!")
            setMBTI("ENFP")
            addPostings(
                Posting(R.drawable.golden_state_warriors, "골든스테이트 워리어스 우승")
            )
        }
    }

    private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra(IntentKey.USER) as? User ?: emptyUser

        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        idTextView.text = user.id
        nameTextView.text = user.name
        mbtiTextView.text = user.mbti
        descriptionTextView.text = user.description
        profileImageView.setImageResource(user.profileDrawableId)
        nameEditButton.editButton.apply {
            text = NAME_EDIT
            setOnClickListener { showInputDialog(NAME_EDIT, nameTextView) }
        }
        mbtiEditButton.editButton.apply {
            text = MBTI_EDIT
            setOnClickListener { showInputDialog(MBTI_EDIT, mbtiTextView) }
        }
        descriptionEditButton.editButton.apply {
            text = DESCRIPTION_EDIT
            setOnClickListener { showInputDialog(DESCRIPTION_EDIT, descriptionTextView) }
        }
        postingCountLayout.apply {
            countTextView.text = user.postings.size.toString()
            titleTextView.text = POSTING
        }
        followerCountLayout.apply {
            countTextView.text = getFollow().toString()
            titleTextView.text = FOLLOWER
        }
        followingCountLayout.apply {
            countTextView.text = getFollow().toString()
            titleTextView.text = FOLLOWING
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
            putExtra(IntentKey.POSTING, posting)
        }
        startActivity(intent)
    }

    fun onMyPageBackspaceButtonClick(view: View) {
        finish()
    }

    private fun showInputDialog(type: String, textView: TextView) {
        val input = EditText(this).apply {
            setSingleLine()
            setText(textView.text.toString())
        }
        AlertDialog.Builder(this).apply {
            setTitle(type)
            setView(input)
            setPositiveButton(CONFIRM) { _, _ ->
                textView.text = input.text.toString()
            }
            setNegativeButton(CANCEL) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }
}