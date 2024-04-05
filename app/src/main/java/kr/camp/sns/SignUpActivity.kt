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
                    binding.signUpIdeditText.setBackgroundResource(R.drawable.rectangle)
                } else {
                    binding.signUpIdcheckButton.background =
                        ContextCompat.getDrawable(this@SignUpActivity, R.drawable.btn_round2)
                    binding.signUpIdcheckButton.isEnabled = false
                    binding.signUpIdeditText.setBackgroundResource(R.drawable.rectangle2)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )
        binding.signUpPasswordeditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (isRegularPassword(password.toString())) {
                    binding.signUpPasswordeditText.setBackgroundResource(R.drawable.rectangle)
                } else {
                    binding.signUpPasswordeditText.setBackgroundResource(R.drawable.rectangle2)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        )

        binding.signUpIdcheckButton.setOnClickListener {

            if (id.isBlank()) {
                Toast.makeText(this, getString(R.string.inputId), Toast.LENGTH_SHORT).show()
            } else if (userRegistry.isUser(id.toString())) {
                Toast.makeText(this, getString(R.string.usedId), Toast.LENGTH_SHORT).show()
            } else if (!isRegularId(id.toString())) {
                Toast.makeText(this, getString(R.string.isRegularId), Toast.LENGTH_SHORT).show()
            } else {
                idCheck = true
                binding.signUpIdcheckButton.isEnabled = false
                binding.signUpIdeditText.isEnabled = false
                binding.signUpIdcheckButton.text = getString(R.string.checked)
                binding.signUpIdcheckButton.background =
                    ContextCompat.getDrawable(this, R.drawable.btn_round2)
                Toast.makeText(this, getString(R.string.usableId), Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpSignupButton.setOnClickListener {
            val user = User(id.toString(), password.toString(), name.toString())

            if (id.isBlank()) {
                Toast.makeText(this, getString(R.string.inputId), Toast.LENGTH_SHORT).show()
            } else if (name.isBlank()) {
                Toast.makeText(this, getString(R.string.inputName), Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(this, getString(R.string.inputPassword), Toast.LENGTH_SHORT).show()
            } else if (!isRegularPassword(password.toString())) {
                Toast.makeText(this, getString(R.string.isRegularPassword), Toast.LENGTH_SHORT).show()
            } else if (repassword.isBlank()) {
                Toast.makeText(this, getString(R.string.inputRePassword), Toast.LENGTH_SHORT).show()
            } else if (password.toString()
                != repassword.toString()
            ) {
                Toast.makeText(this, getString(R.string.checkRePassword), Toast.LENGTH_SHORT).show()
            } else if (!idCheck) {
                Toast.makeText(this, getString(R.string.pleaseIdCheck), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.signUpComplete), Toast.LENGTH_SHORT).show()
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