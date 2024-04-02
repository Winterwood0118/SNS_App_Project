package kr.camp.sns

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.activity.MyPageActivity
import kr.camp.sns.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        openMyPageActivity()
    }

    private fun openMyPageActivity() {
        val intent = Intent(this, MyPageActivity::class.java)
        startActivity(intent)
    }
}