package kr.camp.sns.activity

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.R
import kr.camp.sns.activity.adapter.MyPagePostingAdapter
import kr.camp.sns.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMyPageBinding.inflate(layoutInflater) }

    private lateinit var gridView: GridView
    private lateinit var myPagePostingAdapter: MyPagePostingAdapter

    private val images = listOf(
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry,
        R.drawable.stephen_curry
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        gridView = binding.gridView
        myPagePostingAdapter = MyPagePostingAdapter(this, images)
        gridView.adapter = myPagePostingAdapter
    }
}