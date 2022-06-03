package com.ml.mlkit.presentation.custom_view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.ml.mlkit.R
import com.ml.mlkit.databinding.ToolbarLayoutLargeTitleBinding
import com.ml.mlkit.presentation.extensions.visible


class ToolbarLargeTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomToolbar(context, attrs) {

    private lateinit var binding: ToolbarLayoutLargeTitleBinding

    override fun createToolbar() {
        binding =
            ToolbarLayoutLargeTitleBinding.inflate(LayoutInflater.from(context), this, false)
        binding.apply {
            /**** title ****/
            title.text = customTitle
            setTextViewColorInt(title, customTitleColor.correctColor(context, R.color.black))

            /**** right element ****/
            customRightItem?.let {
                rightItem.setImageDrawable(it)
                rightItem.visible(true)
                enableRightItem(customRightItemEnabled)
            }

        //    setDrawableColorInt(rightItem, customRightItemColor.correctColor(context, R.color.black))

            rightItem.setOnClickListener {
                rightItemClick()
            }

            addView(root)
        }
    }

    override fun setOnRightClickListener(rightClick: () -> Unit) {
        this.rightItemClick = rightClick
    }

    override fun setRightItemColor(@ColorRes colorId: Int) {
        setDrawableColorRes(binding.rightItem, colorId)
    }

    override fun enableRightItem(value: Boolean) {
        binding.rightItem.isEnabled = value
//        setDrawableColorInt(
//            binding.rightItem,
//            if (value)
//                customRightItemColor
//            else ContextCompat.getColor(context, R.color.gray_text)
//        )
    }

    override fun visibleRightItem(value: Boolean) {
        binding.rightItem.visible(value)
    }

    override fun setTitleText(text: String) {
        binding.title.text = text
    }

    override fun setTitleColor(@ColorRes colorId: Int) {
        setTextViewColorRes(binding.title, colorId)
    }

    override fun setRightItem(@DrawableRes drawableId: Int) {
        setDrawableDrawableInt(binding.rightItem, drawableId)
        binding.rightItem.visible(true)
    }

}