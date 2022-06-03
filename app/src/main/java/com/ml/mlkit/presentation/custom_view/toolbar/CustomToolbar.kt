package com.ml.mlkit.presentation.custom_view.toolbar

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ml.mlkit.R

abstract class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    protected var customTitle: String? = null
    protected var customTitleColor: Int = -1

    protected var customProgress:View? = null

    protected var customSubTitle: String? = null
    protected var customSubtitleColor: Int = -1

    protected var customLeftItem: Drawable? = null

    protected var customRightItem: Drawable? = null
    protected var customRightItemColor: Int = -1
    protected var customRightItemText: String? = null
    protected var customRightItemEnabled: Boolean = true

    protected var leftItemClick: () -> Unit = { (context as Activity).onBackPressed() }

    protected var rightItemClick: () -> Unit = {}

    protected var doOnTextChanged: (String, Int, Int, Int) -> Unit =
        { _: String, _: Int, _: Int, _: Int -> }

    init {
        val attr: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, 0, 0)

        customTitle = attr.getString(R.styleable.CustomToolbar_title)

        customTitleColor = attr.getColor(R.styleable.CustomToolbar_titleColor, -1)

        customSubTitle = attr.getString(R.styleable.CustomToolbar_subTitle)

        customSubtitleColor = attr.getColor(R.styleable.CustomToolbar_subtitleColor, -1)

        customLeftItem = attr.getDrawable(R.styleable.CustomToolbar_leftItem)
            ?: AppCompatResources.getDrawable(context, R.drawable.ic_arrow_back_black)

        customRightItem = attr.getDrawable(R.styleable.CustomToolbar_rightItem)

        customRightItemColor = attr.getColor(R.styleable.CustomToolbar_rightItemColor, -1)

        customRightItemText = attr.getString(R.styleable.CustomToolbar_rightItemText)

        customRightItemEnabled = attr.getBoolean(R.styleable.CustomToolbar_rightItemEnabled, true)

        attr.recycle()

        this.createToolbar()
    }

    abstract fun createToolbar()

    protected fun setDrawableDrawableInt(imageView: ImageView, @DrawableRes drawableId: Int) {
        imageView.setImageDrawable(ContextCompat.getDrawable(context, drawableId))
    }

    protected fun setDrawableColorInt(imageView: ImageView, @ColorInt color: Int) {
        imageView.setColorFilter(color)
    }

    protected fun setDrawableColorRes(imageView: ImageView, @ColorRes color: Int) {
        imageView.setColorFilter(ContextCompat.getColor(context, color))
    }

    protected fun setTextViewColorRes(view: TextView, @ColorRes colorRes: Int) {
        view.setTextColor(ContextCompat.getColor(context, colorRes))
    }

    protected fun setTextViewColorInt(view: TextView, @ColorInt colorInt: Int) {
        view.setTextColor(makeSelector(colorInt))
    }

    open fun setOnRightClickListener(rightClick: () -> Unit) {
        this.rightItemClick = rightClick
    }

    open fun setOnLeftClickListener(leftClick: () -> Unit) {
        this.leftItemClick = leftClick
    }

    open fun setOnTextChanged(doOnTextChanged: (text: String, start: Int, before: Int, count: Int) -> Unit) {
        this.doOnTextChanged = doOnTextChanged
    }

    open fun setOnActionListener(doActionListener: ((v: TextView?, actionId: Int, event: KeyEvent?) -> Boolean)?) {}

    open fun setTitleColor(@ColorRes colorId: Int) {}
    open fun setSubtitleColor(@ColorRes colorId: Int) {}
    open fun setRightItemColor(@ColorRes colorId: Int) {}
    open fun setRightItem(@DrawableRes drawableId: Int) {}
    open fun setLeftItem(@DrawableRes drawableId: Int) {}
    open fun setRightItemText(text: String) {}
    open fun setTitleText(text: String) {}
    open fun setSubtitleText(text: String) {}
    open fun enableRightItem(value: Boolean) {}
    open fun isRightItemEnabled(): Boolean = false
    open fun visibleRightItem(value: Boolean) {}
    open fun enableLeftItem(value: Boolean) {}
    open fun visibleLeftItem(value: Boolean) {}

    open fun makeSelector(color: Int): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        )

        val colors = intArrayOf(color, Color.GRAY)

        return ColorStateList(states, colors)
    }
}