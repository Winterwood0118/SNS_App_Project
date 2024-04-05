package kr.camp.sns.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.R
import kr.camp.sns.activity.adapter.MyPagePostingAdapter
import kr.camp.sns.intent.IntentKey
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityMyPageBinding
import kr.camp.sns.databinding.LayoutCounterBinding

class MyPageActivity : AppCompatActivity() {

    private companion object {
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
    private var isLogin = false

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishWithAnimation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra(IntentKey.USER) as? User ?: emptyUser
        isLogin = intent.getBooleanExtra(IntentKey.LOGIN, false)

        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        idTextView.text = user.id
        nameTextView.text = user.name
        mbtiTextView.text = user.mbti
        descriptionTextView.text = user.description
        profileImageView.setImageResource(user.profileDrawableId)

        initEditButton(nameEditButton.editButton, getString(R.string.name_edit), nameTextView)
        initEditButton(mbtiEditButton.editButton, getString(R.string.mbti_edit), mbtiTextView)
        initEditButton(descriptionEditButton.editButton, getString(R.string.description_edit), descriptionTextView)

        initCounter(postingCounter, user.postings.size, getString(R.string.posting))
        initCounter(followerCounter, getFollow(), getString(R.string.follower))
        initCounter(followingCounter, getFollow(), getString(R.string.following))

        postingListView.apply {
            val myPagePostingAdapter = MyPagePostingAdapter(this@MyPageActivity, user.postings)
            adapter = myPagePostingAdapter
            setOnItemClickListener { _, _, position, _ ->
                val posting = myPagePostingAdapter.getItem(position)
                showPostingPopup(posting)
            }
        }
        onBackPressedDispatcher.addCallback(this@MyPageActivity, backPressedCallback)
    }

    private fun initEditButton(button: Button, title: String, textView: TextView) = with(button) {
        if (isLogin) {
            text = title
            setOnClickListener { showInputDialog(title, textView) }
        } else {
            visibility = View.GONE
        }
    }

    private fun initCounter(layoutCounter: LayoutCounterBinding, count: Int, title: String) = with(layoutCounter) {
        countTextView.text = count.toString()
        titleTextView.text = title
    }

    private fun showPostingPopup(posting: Posting) {
        val intent = Intent(this, MyPagePostingPopupActivity::class.java).apply {
            putExtra(IntentKey.POSTING, posting)
        }
        startActivity(intent)
    }

    private fun showInputDialog(type: String, textView: TextView) {
        val input = EditText(this).apply {
            setSingleLine()
            setText(textView.text.toString())
        }
        AlertDialog.Builder(this).apply {
            setTitle(type)
            setView(input)
            setPositiveButton(getString(R.string.confirm)) { _, _ ->
                textView.text = input.text.toString()
            }
            setNegativeButton(getString(R.string.cancle)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    fun onMyPageBackspaceButtonClick(view: View) {
        finishWithAnimation()
    }

    private fun finishWithAnimation() {
        finish()
        showAnimation()
    }

    private fun showAnimation() {
        overridePendingTransition(R.anim.slide_right_start, R.anim.slide_right_end)
    }
}