package kr.camp.sns
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.databinding.ActivityMainBinding
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // val currentPosting = intent.getSerializableExtra("") as Posting

    }
}