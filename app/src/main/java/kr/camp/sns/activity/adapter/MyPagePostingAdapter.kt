package kr.camp.sns.activity.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.setPadding
import kr.camp.sns.data.Posting

class MyPagePostingAdapter(
    private val context: Context,
    private val postings: List<Posting>
) : BaseAdapter() {

    override fun getCount(): Int = postings.size

    override fun getItem(position: Int): Posting = postings[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView = convertView as? ImageView ?: ImageView(context).apply {
            adjustViewBounds = true
            setPadding(3)
        }
        val imageDrawableId = getItem(position).imageDrawableId
        imageView.setImageResource(imageDrawableId)
        return imageView
    }
}