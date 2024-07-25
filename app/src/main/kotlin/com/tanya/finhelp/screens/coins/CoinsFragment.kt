package com.tanya.finhelp.screens.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.FragmentCoinsBinding
import com.tanya.finhelp.domain.Coin
import com.tanya.finhelp.screens.coinInfo.CoinInfoBottomSheet
import com.tanya.finhelp.screens.coins.adapter.CoinsAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG_COIN_INFO_BOTTOM_SHEET = "TAG_COIN_INFO_BOTTOM_SHEET"
const val COIN_KEY = "Coin"

@AndroidEntryPoint
class CoinsFragment : Fragment(R.layout.fragment_coins) {

    private var _binding: FragmentCoinsBinding? = null
    private val binding get() = _binding!!
    private val vm: CoinsVM by viewModels()
    private val adapter = CoinsAdapter { coin ->
        navigateToCoinInfo(coin)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)

        bindUI()
        observeVM()

        return binding.root
    }

    private fun bindUI() {
        binding.recycler.adapter = adapter
    }

    private fun observeVM() {
        vm.state.observe(viewLifecycleOwner) { coins ->
            adapter.submitList(coins)
        }
        vm.getCoins()
    }

    private fun navigateToCoinInfo(coin: Coin) {
        CoinInfoBottomSheet().apply {
            arguments = bundleOf(COIN_KEY to coin)
        }.show(childFragmentManager, TAG_COIN_INFO_BOTTOM_SHEET)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
