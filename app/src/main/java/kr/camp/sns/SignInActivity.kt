package kr.camp.sns

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.camp.sns.databinding.ActivityMainBinding
import kr.camp.sns.databinding.ActivitySignInBinding

@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    var resultLauncher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->        // it -> result
        if (result.resultCode == RESULT_OK) {
            val id = result.data?.getStringExtra("ida") ?: ""                // 아이디 수신 혹은 ""
            val passworda = result.data?.getStringExtra("pas") ?: ""    // 비밀번호 수신 혹은 ""

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 로그인 버튼을 눌렀을 때의 작동
        binding.loginButton.setOnClickListener {
            // 메인 페이지로 이동
            // val intent = Intent(this, MainActivity::class.java)
            // intent로 id 값 전달
            val id = binding.idEditText.toString()
            intent.putExtra(Intent.EXTRA_TEXT, id)
            // startActivity(intent)
            // overridePendingTransition (시작할때 애니메이션, 끝날때 애니메이션)
            overridePendingTransition(R.anim.slide_right_end, R.anim.slide_left_end)
        }

        // 가입하기 텍스트를 눌렀을 떄의 작동
        binding.signInTextView.setOnClickListener {
            // val intent = Intent(this, SignUpActivity::class.java)
            // resultLauncher.launch(intent)
        }

    }

}