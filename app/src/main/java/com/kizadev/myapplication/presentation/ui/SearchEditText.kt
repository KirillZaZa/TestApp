package com.kizadev.myapplication.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText

class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
): AppCompatEditText(context, attrs, defStyleAttrs) {


    private lateinit var keyImeChangeListener: KeyImeChange
    var isOpened = true




    fun setOnKeyImeChangeListener(listener: KeyImeChange){
        keyImeChangeListener = listener
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK && event!!.action == KeyEvent.ACTION_DOWN){
            keyImeChangeListener.onKeyIme(keyCode, event)
            isOpened = !isOpened
        }


        return super.onKeyPreIme(keyCode, event)
    }

    override fun onEditorAction(actionCode: Int) {
        if(actionCode == EditorInfo.IME_ACTION_GO){
            keyImeChangeListener.onKeyIme(actionCode, null)
        }
    }




    interface KeyImeChange{
        fun onKeyIme(keyCode: Int, event: KeyEvent?)
    }


}