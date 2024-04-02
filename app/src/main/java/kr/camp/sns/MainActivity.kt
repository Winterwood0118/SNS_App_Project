package kr.camp.sns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}



/*
1. 메인 텍스트뷰 내부의 글: username + post 인데 spannableString 객체를 이용해 포스트는 노말, 유저네임은 볼드로 바꾸기
2. 리사이클러 뷰 어댑터 만들기
3. 유저 추천?은 리사이클러 뷰 쓸지 그냥 include 상태로 내버려 둘 지 고민
4. 원 내부에



 */