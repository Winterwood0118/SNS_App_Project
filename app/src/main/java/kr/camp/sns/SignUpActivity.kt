package kr.camp.sns

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivitySignUpBinding
import kr.camp.sns.intent.IntentKey
import kr.camp.sns.registry.UserRegistry
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private val userRegistry = UserRegistry.getInstance()

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = binding.signUpIdeditText.text
        val password = binding.signUpPasswordeditText.text
        val repassword = binding.signUpRepasswordeditText.text
        val name = binding.signUpNameeditText.text
        var idCheck = false

        binding.signUpIdeditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularId(id.toString())) {
                    binding.signUpIdcheckButton.setBackgroundResource(R.drawable.btn_round)
                    binding.signUpIdcheckButton.isEnabled = true
                    binding.signUpIdeditText.setBackgroundResource(R.drawable.true_box)
                } else {
                    binding.signUpIdcheckButton.background =
                        ContextCompat.getDrawable(this@SignUpActivity, R.drawable.btn_round2)
                    binding.signUpIdcheckButton.isEnabled = false
                    binding.signUpIdeditText.setBackgroundResource(R.drawable.false_box)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )
        binding.signUpRepasswordeditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRegularPassword(password.toString())) {
                    binding.signUpPasswordeditText.setBackgroundResource(R.drawable.true_box)
                } else {
                    binding.signUpPasswordeditText.setBackgroundResource(R.drawable.false_box)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )

        binding.signUpIdcheckButton.setOnClickListener {

            if (id.isBlank()) {
                Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (userRegistry.isUser(id.toString())) {
                Toast.makeText(this, "이미 존재하는 아이디 입니다", Toast.LENGTH_SHORT).show()
            } else if (!isRegularId(id.toString())) {
                Toast.makeText(this, "올바른 아이디 형식이 아닙니다", Toast.LENGTH_SHORT).show()
            } else {
                idCheck = true
                binding.signUpIdcheckButton.isEnabled = false
                binding.signUpIdeditText.isEnabled = false
                binding.signUpIdcheckButton.text = "확인완료"
                binding.signUpIdcheckButton.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_round2)
                Toast.makeText(this, "사용 가능한 아이디 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpSignupButton.setOnClickListener {
            val user = User(id.toString(), password.toString(), name.toString())

            if (id.isBlank()) {
                Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (name.isBlank()) {
                Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (!isRegularPassword(password.toString())) {
                Toast.makeText(this, "올바른 비밀번호 형식이 아닙니다", Toast.LENGTH_SHORT).show()
            } else if (repassword.isBlank()) {
                Toast.makeText(this, "비밀번호를 한번 더 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (password.toString()
                != repassword.toString()
            ) {
                Toast.makeText(this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
            } else if (!idCheck) {
                Toast.makeText(this, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent().apply {
                    putExtra(IntentKey.USER, user)
                }
                userRegistry.addUser(user)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }
        }

    }

    private fun isRegularId(id: String): Boolean {
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{7,10}$"
        return Pattern.matches(idPattern, id)
    }

    private fun isRegularPassword(password: String): Boolean {
        val passwordPattern =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$"
        return Pattern.matches(passwordPattern, password)
    }

}