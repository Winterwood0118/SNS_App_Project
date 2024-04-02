@file:Suppress("DEPRECATION")

package kr.camp.sns

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivitySignInBinding
import kr.camp.sns.registry.UserRegistry
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val id = it.data?.getStringExtra("id") ?: ""                // 아이디 수신 혹은 ""
                val passworde = it.data?.getStringExtra("password") ?: ""    // 비밀번호 수신 혹은 ""
                binding.idEditText.setText(id)
                binding.passwordEditText.setText(passworde)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 로그인 버튼을 눌렀을 때의 작동
        binding.loginButton.setOnClickListener {
            // 메인 페이지로 이동
            val intent = Intent(this, MainActivity::class.java)
            val user = UserRegistry()
            // intent로 id 값 전달
            // binding함수 작성
            val idEdit = binding.idEditText.toString()
            // dataClass로 이용해야 될 것 같은데....
            intent.putExtra("id", idEdit)
            // 로그인 유효성 검사는 data class를 이용
            // 애니메이션 작동하면서 메인액티비티로 이동
            if(!isRegularId() && !isRegularPassword()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (!isRegularId()) {
                Toast.makeText(this, "영어와 숫자를 포함하여 8~20자리로 입력해야 합니다", Toast.LENGTH_LONG).show()
            } else if(!isRegularPassword()) {
                Toast.makeText(this, "영어와 숫자, 특수문자를 조합하여 8~20자리로 입력해야 합니다", Toast.LENGTH_LONG).show()
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

        binding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경되기 전 호출
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 마다 호출
                isRegularId()
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경된 후 호출
            }

        })

        // Log.d("edit", binding.idEditText.text.toString())
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경되기 전 호출
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 마다 호출
                isRegularPassword()
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트가 변경된 후 호출
            }

        })
    }

    // overridePendingTransition (시작할때 애니메이션, 끝날때 애니메이션)
    // overridePendingTransition을 함수로 만들어 밖으로 뺌
    fun loginStart() {
        overridePendingTransition(R.anim.slide_right_end, R.anim.slide_left_end)
    }


    // 아이디 유효성 검사 함수
    private fun isRegularId(): Boolean {
        val idEnglish = binding.idEditText.text.toString().trim()
        var idEdit = binding.idEditText
        // 영어, 숫자
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
        val pattern = Pattern.matches(idPattern, idEnglish)
        if (pattern) {
            // 아이디의 형식이 맞을 경우
            //텍스트 박스가 파랑색으로 표시
            idEdit.setBackgroundResource(R.drawable.true_box)
            return true
        } else {
            // 아이디의 형식이 틀릴 경우
            // 텍스트 박스가 빨간색으로 표시
            idEdit.setBackgroundResource(R.drawable.false_box)
            return false
        }
    }


    // 비밀번호 유효성 검사 함수
    private fun isRegularPassword(): Boolean {
        val passwordEnglish = binding.passwordEditText.text.toString().trim() // 띄어쓰기 삭제
        val notPasswordTextView = binding.notPassword
        // 영어, 특수문자, 8~15자리
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$"
        val pwdPattern = Pattern.matches(passwordPattern, passwordEnglish)
        val passwordEdit = binding.passwordEditText
        if (pwdPattern) {
            // 아이디의 형식이 맞을 경우
            //텍스트 박스가 파랑색으로 표시
            notPasswordTextView.visibility = View.GONE
            passwordEdit.setBackgroundResource(R.drawable.true_box)
            return true
        } else {
            // 아이디의 형식이 틀릴 경우
            // 텍스트 박스가 빨간색으로 표시
            notPasswordTextView.visibility = View.VISIBLE
            passwordEdit.setBackgroundResource(R.drawable.false_box)
            return false
        }
    }
}