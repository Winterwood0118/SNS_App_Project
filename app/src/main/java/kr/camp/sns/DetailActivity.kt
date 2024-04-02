package kr.camp.sns

import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.camp.sns.data.Posting
import kr.camp.sns.databinding.ActivityDetailBinding
import kr.camp.sns.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val currentPosting = intent.getSerializableExtra("") as Posting

    }
}