@file:Suppress("DEPRECATION")

package kr.camp.sns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivitySignInBinding
import kr.camp.sns.intent.IntentKey
import kr.camp.sns.registry.UserRegistry
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val user = it.data?.getSerializableExtra(IntentKey.USER) as User
                val id = user.id
                val password = user.password
                binding.idEditText.setText(id)
                binding.passwordEditText.setText(password)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userRegistry = UserRegistry.getInstance()

        // 로그인 버튼을 눌렀을 때의 작동
        binding.loginButton.setOnClickListener {
            // 메인 페이지로 이동
            val inputId = binding.idEditText.text.toString()
            val inputPassword = binding.passwordEditText.text.toString()
            var user = userRegistry.findUserByIdAndPassword(inputId, inputPassword)
            if (user == null) {
                toast("잘못된 아이디와 비밀번호 입니다")
            } else if (!isRegularId()) {
                toast("아이디의 조합이 틀렸습니다")
            } else if (!isRegularPassword()) {
                toast("비밀번호의 조합이 틀렸습니다")
            } else {
                // 메인으로 넘김
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(IntentKey.USER, user) // 로그인 성공한 데이터가 모두 넘겅감
                intent.putExtra(IntentKey.LOGIN, true) // 로그인 이 맞으면 넘김
                setResult(Activity.RESULT_OK, intent) // 좀 있다 검색
                finish()
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
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 마다 호출
                isRegularPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    // overridePendingTransition (시작할때 애니메이션, 끝날때 애니메이션)
    // overridePendingTransition을 함수로 만들어 밖으로 뺌
    fun loginStart() {
        overridePendingTransition(R.anim.none, R.anim.slide_right_end)
    }


    // 아이디 유효성 검사 함수
    private fun isRegularId(): Boolean {
        val idEnglish = binding.idEditText.text.toString().trim()
        var idEdit = binding.idEditText
        // 영어, 숫자
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{7,10}$"
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
        // 영어, 특수문자, 8~20자리
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
    fun toast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}