package com.tanya.finhelp.screens.coins.adapter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.ItemCoinBinding
import com.tanya.finhelp.databinding.ItemSkeletonCoinBinding
import com.tanya.finhelp.domain.BaseRecyclerItem
import com.tanya.finhelp.domain.Coin

private const val ANIMATION_DURATION = 1000L
private const val ANIMATION_REPEAT = 10

abstract class BaseVH(binding: ViewBinding) : ViewHolder(binding.root) {
    abstract fun bind(baseRecyclerItem: BaseRecyclerItem)
}

class CoinVH(private val binding: ItemCoinBinding, private var onClick: (Coin) -> Unit) : BaseVH(binding) {

    override fun bind(baseRecyclerItem: BaseRecyclerItem) = with(binding) {
        if (baseRecyclerItem is Coin) {
            Glide.with(root).load(baseRecyclerItem.image).circleCrop().into(logoImage)
            symbolText.text = baseRecyclerItem.symbol
            nameCompanyText.text = baseRecyclerItem.name
            priceText.text = baseRecyclerItem.price
            priceChangeText.setTextColor(baseRecyclerItem.priceChange.color)
            priceChangeText.text = baseRecyclerItem.priceChange.value
            priceChangePercentageText.setTextColor(baseRecyclerItem.priceChangePercentage.color)
            priceChangePercentageText.text = baseRecyclerItem.priceChangePercentage.value
            root.setOnClickListener {
                onClick.invoke(baseRecyclerItem)
            }
        }
    }
}

class SkeletonVH(private val binding: ItemSkeletonCoinBinding) : BaseVH(binding) {

    override fun bind(baseRecyclerItem: BaseRecyclerItem) {
        val colorFrom: Int = ContextCompat.getColor(binding.root.context, R.color.gray)
        val colorTo: Int = ContextCompat.getColor(binding.root.context, R.color.gray_light)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.setDuration(ANIMATION_DURATION)
        colorAnimation.addUpdateListener { animator ->
            with(binding) {
                logoImage.setBackgroundColor(animator.animatedValue as Int)
                skelSymbolText.setBackgroundColor(animator.animatedValue as Int)
                skelNameCompanyText.setBackgroundColor(animator.animatedValue as Int)
                skelPrice.setBackgroundColor(animator.animatedValue as Int)
                skelPriceChangeText.setBackgroundColor(animator.animatedValue as Int)
                skelPriceChangePercentageText.setBackgroundColor(animator.animatedValue as Int)
            }
        }
        colorAnimation.repeatCount = ANIMATION_REPEAT
        colorAnimation.repeatMode = ValueAnimator.REVERSE
        colorAnimation.start()
    }
}
