package com.yuri_berezhnyi.abzapp.ui.utils.customeView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yuri_berezhnyi.abzapp.R

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        isWork(true)
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    fun isWork(isWork: Boolean) {
        if (isWork) {
            setBackgroundResource(R.drawable.bg_edit)
            setHintTextColor(ContextCompat.getColor(context, R.color.gray))
        } else {
            setBackgroundResource(R.drawable.bg_edit_error)
            setHintTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }
}
