package com.guvyerhopkins.livefront.core.extensions

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun CharSequence.toSpannable() = SpannableStringBuilder(this)

operator fun SpannableStringBuilder.set(
    old: CharSequence,
    new: SpannableStringBuilder
): SpannableStringBuilder {
    val index = indexOf(old.toString())
    return this.replace(index, index + old.length, new, 0, new.length)
}

fun TextView.clickableSpan(linkText: CharSequence, onClick: () -> Unit) {
    val fullText = text.toSpannable()
    fullText[linkText] = linkText.toSpannable().apply {
        setSpan(object : ClickableSpan() {

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = this@clickableSpan.currentTextColor
            }

            override fun onClick(p0: View) {
                onClick()
            }
        }, 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    text = fullText
    movementMethod = LinkMovementMethod.getInstance()
}
