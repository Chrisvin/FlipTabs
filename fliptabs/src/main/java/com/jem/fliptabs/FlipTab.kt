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

    private val leftSelectedDrawable by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.tab_left_selected, null)
        } else {
            resources.getDrawable(R.drawable.tab_left_selected)
        }
    }
    private val rightSelectedDrawable  by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.tab_right_selected, null)
        } else {
            resources.getDrawable(R.drawable.tab_right_selected)
        }
    }

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
                    //TODO: Find out a better alternative to changing Background in the middle of animation (might result in dropped frame/stutter)
                    if (isLeftSelected) {
                        tab_selected.text = rightTabText
                        tab_selected.background = rightSelectedDrawable
                        tab_selected.scaleX = -1f
                    } else {
                        tab_selected.text = leftTabText
                        tab_selected.background = leftSelectedDrawable
                        tab_selected.scaleX = 1f
                    }
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