package com.ml.mlkit.presentation.custom_view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.ml.mlkit.R
import com.ml.mlkit.databinding.ToolbarLayoutTwoIconBinding
import com.ml.mlkit.presentation.extensions.show
import com.ml.mlkit.presentation.extensions.visible

class ToolbarStandard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomToolbar(context, attrs) {

    private lateinit var binding: ToolbarLayoutTwoIconBinding

    override fun createToolbar() {
        binding = ToolbarLayoutTwoIconBinding.inflate(LayoutInflater.from(context), this, false)
        binding.apply {

            //*** title ***
            title.text = customTitle
            setTextViewColorInt(title, customTitleColor.correctColor(context, R.color.black))

            //*** subtitle ***
            subtitle.text = customSubTitle
            subtitle.show(!customSubTitle.isNullOrEmpty())
            setTextViewColorInt(
                subtitle,
                customSubtitleColor.correctColor(context, R.color.granite)
            )

            //*** left element ***
            customLeftItem?.let {
                leftItem.visible(true)
                leftItem.setImageDrawable(it)
            }

            leftItem.setOnClickListener {
                leftItemClick()
            }

            //*** right element ***
            customRightItem?.let {
                rightItem.setImageDrawable(it)
                rightItem.visible(true)
                enableRightItem(customRightItemEnabled)
            }

            setDrawableColorInt(
                rightItem,
                customRightItemColor.correctColor(context, R.color.black)
            )

            rightItem.setOnClickListener {
                rightItemClick()
            }

            addView(root)
        }
    }

    override fun setOnRightClickListener(rightClick: () -> Unit) {
        this.rightItemClick = rightClick
    }

    override fun setOnLeftClickListener(leftClick: () -> Unit) {
        this.leftItemClick = leftClick
    }

    override fun setRightItemColor(@ColorRes colorId: Int) {
        setDrawableColorRes(binding.rightItem, colorId)
    }

    override fun enableRightItem(value: Boolean) {
        binding.rightItem.isEnabled = value
        setDrawableColorInt(
            binding.rightItem,
            if (value)
                customRightItemColor
            else ContextCompat.getColor(context, R.color.gray_text)
        )
    }

    override fun isRightItemEnabled(): Boolean {
        return binding.rightItem.isEnabled
    }

    override fun visibleRightItem(value: Boolean) {
        binding.rightItem.visible(value)
    }

    override fun setTitleText(text: String) {
        binding.title.text = text
    }

    override fun setSubtitleText(text: String) {
        binding.subtitle.text = text
    }

    override fun setTitleColor(@ColorRes colorId: Int) {
        setTextViewColorRes(binding.title, colorId)
    }

    override fun setSubtitleColor(@ColorRes colorId: Int) {
        setTextViewColorRes(binding.subtitle, colorId)
    }

    override fun setLeftItem(@DrawableRes drawableId: Int) {
        setDrawableDrawableInt(binding.leftItem, drawableId)
    }

    override fun setRightItem(@DrawableRes drawableId: Int) {
        setDrawableDrawableInt(binding.rightItem, drawableId)
        binding.rightItem.visible(true)
    }

    override fun enableLeftItem(value: Boolean) {
        binding.leftItem.isEnabled = value
    }

    override fun visibleLeftItem(value: Boolean) {
        binding.leftItem.visible(value)
    }

}