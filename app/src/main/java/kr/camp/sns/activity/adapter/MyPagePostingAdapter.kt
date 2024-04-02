package kr.camp.sns.activity.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.setPadding

class MyPagePostingAdapter(
    private val context: Context,
    private val imageDrawableIds: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = imageDrawableIds.size

    override fun getItem(position: Int): Int = imageDrawableIds[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView = convertView as? ImageView ?: run {
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER
                adjustViewBounds = true
                setPadding(3)
            } // xml에서 가져오기
        }
        val imageDrawableId = getItem(position)
        imageView.setImageResource(imageDrawableId)
        return imageView
    }
}