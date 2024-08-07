package com.tanya.finhelp.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val vm: AuthVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        bindUI()
        observeVM()

        return binding.root
    }

    private fun bindUI() {
        if (vm.isUserExist()) {
            findNavController().navigate(R.id.to_coinsFragment)
        }

        binding.logInButton.setOnClickListener {
            vm.logIn(
                email = binding.emailEdit.text.toString(),
                password = binding.passwordEdit.text.toString()
            )
        }

        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.to_registrationFragment)
        }
    }

    private fun observeVM() {
        vm.isLogIn.observe(viewLifecycleOwner) { isLogIn ->
            if (isLogIn) findNavController().navigate(R.id.to_coinsFragment)
        }

        vm.error.observe(viewLifecycleOwner) { error ->
            binding.errorText.text = error
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
