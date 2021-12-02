package com.kizadev.myapplication.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.myapplication.R
import com.kizadev.myapplication.databinding.CustomSearchViewBinding

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private val _viewBinding by viewBinding(CustomSearchViewBinding::bind)

    val searchBinding: CustomSearchViewBinding
        get() = _viewBinding

    init {
        View.inflate(context, R.layout.custom_search_view, this)
        setBackground()

    }


    private fun setBackground() {
        val bg = ContextCompat.getDrawable(context, R.color.purple_200)

        elevation = 4f
        background = bg
    }


}