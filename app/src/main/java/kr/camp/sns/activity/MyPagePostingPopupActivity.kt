package kr.camp.sns.activity

import android.app.Activity
import android.os.Bundle
import android.view.Window
import kr.camp.sns.activity.intent.IntentKey
import kr.camp.sns.data.Posting
import kr.camp.sns.databinding.ActivityMyPagePostingPopupBinding

class MyPagePostingPopupActivity : Activity() {

    private val binding by lazy { ActivityMyPagePostingPopupBinding.inflate(layoutInflater) }

    private lateinit var posting: Posting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        posting = intent.getSerializableExtra(IntentKey.POSTING) as Posting

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        postingImageView.setImageResource(posting.imageDrawableId)
        postingDescriptionTextView.text = posting.description
    }
}