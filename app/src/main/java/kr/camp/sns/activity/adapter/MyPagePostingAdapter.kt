package kr.camp.sns.activity.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kr.camp.sns.R

class MyPagePostingAdapter(
    private val context: Context,
    private val data: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val _view = (R.layout.item_my_page_posting)

            /*convertView as? ImageView ?: run {
            *//*
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.item_my_page_posting, parent, false)
            parent.findViewById(R.id.myPagePostingItem)
            *//*
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                adjustViewBounds = true
                setPadding(3)
            } // xml에서 가져오기
        }*/
        val imageDrawableId = data[position]
        _view?.setImageResource(imageDrawableId)
        return _view!!
    }
}