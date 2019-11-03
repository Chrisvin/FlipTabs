package com.jem.fliptabs

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.SpannableString
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.fliptab.view.*


class FlipTab : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private var isLeftSelected: Boolean = true
    private var animationInProgress: Boolean = false
    private var animationMiddleViewFlippedFlag: Boolean = false
    private val leftTabText get() = tab_left.text.toString()
    private val rightTabText get() = tab_right.text.toString()

    init {
        inflate(context, R.layout.fliptab, this)
        tab_left.setOnClickListener {
            if (!isLeftSelected) {
                flipTabs()
            }
        }
        tab_right.setOnClickListener {
            if (isLeftSelected) {
                flipTabs()
            }
        }
        clipChildren = false
        clipToPadding = false
    }

    private fun flipTabs() {
        if (animationInProgress) return
        tab_selected_container.animate()
            .rotationYBy(if (isLeftSelected) 180f else -180f)
            .setDuration(500)
            .withStartAction {
                animationInProgress = true
                (parent as ViewGroup?)?.clipChildren = false
                (parent as ViewGroup?)?.clipToPadding = false
            }
            .setUpdateListener {
                if (!animationMiddleViewFlippedFlag && it.animatedFraction>0.5) {
                    animationMiddleViewFlippedFlag = true
                    //TODO: Find out a better alternative to settingBackgroundResouce since it causes a slight stutter
                    //Consider using SpannableString and ScaleXSpan on the text directly
                    val startPadding: Int
                    val endPadding: Int
                    if (isLeftSelected) {
                        tab_selected.text = rightTabText
                        tab_selected.setBackgroundResource(R.drawable.tab_right_selected)
                        tab_selected.scaleX = -1f
                        startPadding = resources.getDimensionPixelSize(R.dimen.rightTabPaddingStart)
                        endPadding = resources.getDimensionPixelSize(R.dimen.rightTabPaddingEnd)
                    } else {
                        tab_selected.text = leftTabText
                        tab_selected.setBackgroundResource(R.drawable.tab_left_selected)
                        tab_selected.scaleX = 1f
                        startPadding = resources.getDimensionPixelSize(R.dimen.leftTabPaddingStart)
                        endPadding = resources.getDimensionPixelSize(R.dimen.leftTabPaddingEnd)
                    }
                    tab_selected.setPadding(
                        startPadding,
                        resources.getDimensionPixelSize(R.dimen.tabPaddingTop),
                        endPadding,
                        resources.getDimensionPixelSize(R.dimen.tabPaddingBottom)
                    )
                }
            }
            .withEndAction {
                isLeftSelected = !isLeftSelected
                animationInProgress = false
                animationMiddleViewFlippedFlag = false
                (parent as ViewGroup).clipChildren = true
                (parent as ViewGroup).clipToPadding = true
            }
            .start()
    }
}