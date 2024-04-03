package kr.camp.sns.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import kr.camp.sns.R
import kotlin.math.min

class CircleImageView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)

        val imageResourceId = a.getResourceId(
            R.styleable.CircleImageView_android_src, -1)
        val attrsWidthPx = a.getDimension(
            R.styleable.CircleImageView_android_layout_width, 0f)
        val attrsHeightPx = a.getDimension(
            R.styleable.CircleImageView_android_layout_height, 0f)

        val outerBorderSize = min(attrsWidthPx, attrsHeightPx)
        val innerBorderSize = outerBorderSize - outerBorderSize / 15
        val imageBorderSize = outerBorderSize * 5 / 6

        val outerLayoutParams = LayoutParams(
            outerBorderSize.toInt(),
            outerBorderSize.toInt()
        ).apply {
            gravity = Gravity.CENTER
        }

        val innerLayoutParams = LayoutParams(
            innerBorderSize.toInt(),
            innerBorderSize.toInt()
        ).apply {
            gravity = Gravity.CENTER
        }

        val imageLayoutParams = LayoutParams(
            imageBorderSize.toInt(),
            imageBorderSize.toInt()
        ).apply {
            gravity = Gravity.CENTER
        }

        val outerBorderCardView = CardView(context).apply {
            layoutParams = outerLayoutParams
            radius = outerBorderSize / 2 + 1f
        }

        val outerLayout = FrameLayout(context).apply {
            layoutParams = outerLayoutParams
            background = resources.getDrawable(R.drawable.gradient_color_2)
        }

        val innerBorderCardView = CardView(context).apply {
            layoutParams = innerLayoutParams
            setCardBackgroundColor(Color.WHITE)
            radius = innerBorderSize / 2 + 1f
        }

        val imageBorderCardView = CardView(context).apply {
            layoutParams = imageLayoutParams
            setCardBackgroundColor(Color.TRANSPARENT)
            radius = imageBorderSize / 2 + 1f
        }

        val imageView = ImageView(context).apply {
            layoutParams = imageLayoutParams
            if(imageResourceId != -1) setImageResource(imageResourceId)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        imageBorderCardView.addView(imageView)
        innerBorderCardView.addView(imageBorderCardView)
        outerLayout.addView(innerBorderCardView)
        outerBorderCardView.addView(outerLayout)
        addView(outerBorderCardView)

        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}

