package kr.camp.sns.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.children
import kr.camp.sns.R

class CircleImageView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        background = resources.getDrawable(R.drawable.gradient_color)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val cardView = CardView(context)
        cardView.layoutParams = layoutParams
        cardView.radius = layoutParams.width.toFloat() / 2
        cardView.
        addView(cardView)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}