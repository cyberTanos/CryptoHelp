package com.tanya.finhelp.screens.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.FragmentCoinsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : Fragment(R.layout.fragment_coins) {

    private var _binding: FragmentCoinsBinding? = null
    private val binding get() = _binding!!
    private val vm: CoinsVM by viewModels()
    private val adapter = CoinsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCoinsBinding.inflate(inflater, container, false)

        bindUI()
        observeCoinsLD()

        return binding.root
    }

    private fun bindUI() {
        binding.recycler.adapter = adapter
    }

    private fun observeCoinsLD() {
        vm.state.observe(viewLifecycleOwner) { coins ->
            adapter.submitList(coins)
        }
        vm.getCoins()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
