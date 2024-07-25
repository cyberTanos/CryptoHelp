package com.tanya.finhelp.screens.coinInfo

import android.content.Context.WINDOW_SERVICE
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.BottomSheetCoinInfoBinding
import com.tanya.finhelp.domain.Coin
import com.tanya.finhelp.screens.coins.COIN_KEY

private const val TOP_INDENTATION = 250

class CoinInfoBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_coin_info) {

    private var _binding: BottomSheetCoinInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetCoinInfoBinding.inflate(inflater, container, false)

        bindUI()

        return binding.root
    }

    private fun bindUI() = with(binding) {
        arguments?.getParcelable<Coin>(COIN_KEY).let { coin ->
            Glide.with(root).load(coin?.image).circleCrop().into(logoImage)
            symbolText.text = coin?.symbol
            nameCompanyText.text = coin?.name
            priceText.text = coin?.price
            coin?.priceChange?.let { priceChangeText.setTextColor(it.color) }
            priceChangeText.text = coin?.priceChange?.value
            coin?.priceChangePercentage?.let { priceChangePercentageText.setTextColor(it.color) }
            priceChangePercentageText.text = coin?.priceChangePercentage?.value
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val displayMetrics = DisplayMetrics()
            val windowsManager = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager
            windowsManager.defaultDisplay.getMetrics(displayMetrics)
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = displayMetrics.heightPixels - TOP_INDENTATION
        }
        val behavior = BottomSheetBehavior.from(view?.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
