package kr.camp.sns.view

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kr.camp.sns.R

/**
 * Github Link: https://github.com/TheCodeYard/EllipsizedTextView
 * */

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(context, attrs, defStyleAttr) {

    var ellipsis = getDefaultEllipsis().toString()
    var ellipsisColor = getDefaultEllipsisColor()
    val numMaxLines: Int

    private val ellipsisSpannable: SpannableString
    private val spannableStringBuilder = SpannableStringBuilder()

    init {
        numMaxLines = maxLines
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, 0, 0)
            typedArray?.let {
                ellipsis = typedArray.getString(R.styleable.ExpandableTextView_ellipsis) ?: getDefaultEllipsis().toString()
                ellipsisColor = typedArray.getColor(R.styleable.ExpandableTextView_ellipsisColor, getDefaultEllipsisColor())
                typedArray.recycle()
            }
        }

        ellipsisSpannable = SpannableString(ellipsis)
        ellipsisSpannable.setSpan(ForegroundColorSpan(ellipsisColor), 0, ellipsis.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val availableScreenWidth =
            measuredWidth - compoundPaddingLeft.toFloat() - compoundPaddingRight.toFloat()
        var availableTextWidth = availableScreenWidth * maxLines
        var ellipsizedText = TextUtils.ellipsize(text, paint, availableTextWidth, ellipsize)

        if (ellipsizedText.toString() != text.toString()) {
            availableTextWidth = (availableScreenWidth - paint.measureText(ellipsis)) * maxLines
            ellipsizedText = TextUtils.ellipsize(text, paint, availableTextWidth, ellipsize)
            val defaultEllipsisStart = ellipsizedText.indexOf(getDefaultEllipsis())
            val defaultEllipsisEnd = defaultEllipsisStart + 1
            spannableStringBuilder.clear()
            text = spannableStringBuilder.append(ellipsizedText)
                .replace(defaultEllipsisStart, defaultEllipsisEnd, ellipsisSpannable)
        }
    }

    private fun getDefaultEllipsis(): Char {
        return Typography.ellipsis
    }

    private fun getDefaultEllipsisColor(): Int {
        return textColors.defaultColor
    }
}