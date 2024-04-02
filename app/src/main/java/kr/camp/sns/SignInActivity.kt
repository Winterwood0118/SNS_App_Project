package kr.camp.sns

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.databinding.ActivitySignInBinding
import java.util.regex.Pattern

@Suppress("DEPRECATION")
class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it
            if (it.resultCode == RESULT_OK) {
                val id = it.data?.getStringExtra("id") ?: ""                // 아이디 수신 혹은 ""
                val passworde = it.data?.getStringExtra("password") ?: ""    // 비밀번호 수신 혹은 ""
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
            val idEdit = binding.idEditText.toString()
            intent.putExtra(Intent.EXTRA_TEXT, idEdit)
            if (!isRegularId()) {
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            } else if(!isRegularPassword()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            }else {
                startActivity(intent)
                loginStart()
            }
        }

        // 가입하기 텍스트를 눌렀을 떄의 작동
        binding.signInTextView.setOnClickListener {
            // val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }

    }
    // overridePendingTransition (시작할때 애니메이션, 끝날때 애니메이션)
    // overridePendingTransition을 함수로 만들어 밖으로 뺌
    fun loginStart() {
        overridePendingTransition(R.anim.slide_right_end, R.anim.slide_left_end)
    }



    // 아이디 유효성 검사 함수
    private fun isRegularId(): Boolean {
        val english = binding.idEditText.text.toString().trim()
        // 영어, 글자 수는 7~10자
        val idPattern = "^(?=.*[A-Za-z]){7,10}$"
        val pattern = Pattern.matches(idPattern, english)
        if (pattern) {
            // 아이디의 형식이 맞을 경우
            //텍스트 박스가 파랑색으로 표시
            binding.idEditText.setBackgroundResource(R.drawable.true_box)
            return true
        } else {
            // 아이디의 형식이 틀릴 경우
            // 텍스트 박스가 빨간색으로 표시
            binding.idEditText.setBackgroundResource(R.drawable.false_box)
            return false
        }
    }


    // 비밀번호 유효성 검사 함수
    private fun isRegularPassword(): Boolean {
        val english = binding.passwordEditText.toString().trim() // 띄어쓰기 삭제
        // 영어, 특수문자, 10~15자리
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*[!@#\$%^&+=]){10,15}.*$"
        val pattern = Pattern.matches(passwordPattern, english)
        if (pattern) {
            // 비밀번호의 형식이 맞을 경우
            binding.passwordEditText.setBackgroundResource(R.drawable.true_box)
            return true
        } else {
            binding.passwordEditText.setBackgroundResource(R.drawable.true_box)
            // 비밀번호의 형식이 틀릴 경우
            return false
        }
    }
}