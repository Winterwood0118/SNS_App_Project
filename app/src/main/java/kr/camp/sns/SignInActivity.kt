package kr.camp.sns

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kr.camp.sns.databinding.ActivityMainBinding
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
            } else {
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

    fun idText() {
        binding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경되기 전에 호출된다
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 텍스트가 변경될 때마다 호출된다.
                // 아이디을 입력할 때 실시간으로 이메일 형식을 검사한다.
                isRegularId()
            }

            override fun afterTextChanged(p0: Editable?) {
                // 텍스트가 변경된 후 호출된다
            }

        })
    }

    // 아이디 유효성 검사 함수
    private fun isRegularId(): Boolean {
        val english = binding.idEditText.text.toString().trim()
        // 영어, 글자 수는 7~10자
        val idPattern = "^(?=.*[A-Za-z])[A-Za-z]{7,10}$"
        val pattern = Pattern.matches(idPattern, english)
        if (pattern) {
            // 아이디의 형식이 맞을 경우
            return true
        } else {
            // 아이디의 형식이 틀릴 경우
            return false
        }
    }
}