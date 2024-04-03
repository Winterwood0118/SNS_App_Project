package kr.camp.sns

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.camp.sns.data.Posting
import kr.camp.sns.data.User
import kr.camp.sns.databinding.ActivityMainBinding
import kr.camp.sns.registry.UserRegistry


class MainActivity : AppCompatActivity() {
    private var isLogin = false
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private val postList by lazy {
        mutableListOf(
            binding.mainPost1,
            binding.mainPost2,
            binding.mainPost3,
            binding.mainPost4,
            binding.mainPost5
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    isLogin = result.data?.getBooleanExtra("extra_login", false) ?: false
                }
            }
        binding.mainLoginImageView.setOnClickListener {
            if (isLogin){
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, SignInActivity::class.java)
                startForResult.launch(intent)
            }
        }

        for (i in postList.indices) {
            postList[i].apply {
                val post = itemList[i].second
                val userName = itemList[i].first
                val countOfLike = "${post.likeCount} likes"
                val postString =
                    SpannableStringBuilder("${userName} ${post.description}")
                postString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    userName.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                customPostCountOfLikeTextView.text = countOfLike
                customPostNameTextView.text = userName
                customPostMainTextView.text = postString
                customPostMainImageView.apply{
                    setImageResource(post.imageDrawableId)
                    setOnClickListener {
                        Toast.makeText(this@MainActivity, "${i+1} 번 포스트", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)

                        intent.apply {
                            putExtra("extra_post", post)
                            putExtra("extra_posting_user_name", userName)
                        }

                        startActivity(intent)
                    }
                }
            }
        }
    }
}


/*
1. 메인 텍스트뷰 내부의 글: username + post 인데 spannableString 객체를 이용해 포스트는 노말, 유저네임은 볼드로 바꾸기 << 완료
2. 리사이클러 뷰 어댑터 만들기 << 취소
3. 유저 추천?은 리사이클러 뷰 쓸지 그냥 include 상태로 내버려 둘 지 고민 << 인클루드 상태로
4. 원 내부에 원형으로 이미지 넣기 << 카드뷰로 해결
5.
 */


/*
        binding.mainPostList.adapter = listViewAdapter(this)
*/

/*        binding.mainLoginImageView.setOnClickListener {
            if (isLogin) {
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startForResult.launch(intent)
            }
        }*/


/*    inner class listViewAdapter(val context: Context) : BaseAdapter() {
        override fun getCount(): Int {
            return itemList.count()
        }

        override fun getItem(position: Int): Any {
            return itemList[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if (view == null) {
                view =
                    LayoutInflater.from(context).inflate(R.layout.custom_post_item, parent, false)
            }
            val postImage = view!!.findViewById<ImageView>(R.id.customPostMainImageView)
            val postText = view.findViewById<TextView>(R.id.customPostMainTextView)
            val postLikeCount = view.findViewById<TextView>(R.id.customPostCountOfLikeTextView)

            val postString =
                SpannableStringBuilder("${itemList[position].first} ${itemList[position].second.description}")

            postString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                itemList[position].first.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            val likeCount = "${itemList[position].second.likeCount} likes"

            postLikeCount.text = likeCount
            postImage.setImageResource(itemList[position].second.imageDrawableId)
            postText.text = postString

*//*            view.setOnClickListener {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                startActivity(intent)
            }*//*
            return view
        }
    }*/