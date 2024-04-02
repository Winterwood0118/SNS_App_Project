package kr.camp.sns.activity

import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.R
import kr.camp.sns.activity.adapter.MyPagePostingAdapter
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

    private lateinit var user: User

    private lateinit var idTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var mbtiTextView: TextView
    private lateinit var descriptionTextView: TextView

    private lateinit var gridView: GridView
    private lateinit var myPagePostingAdapter: MyPagePostingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra("User") as User

        setContentView(binding.root)
        init()
    }

    private fun init() {
        gridView = binding.gridView
        myPagePostingAdapter = MyPagePostingAdapter(this, listOf())
        gridView.adapter = myPagePostingAdapter
    }
}