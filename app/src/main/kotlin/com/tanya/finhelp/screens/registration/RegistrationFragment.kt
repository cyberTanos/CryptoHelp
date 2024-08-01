package com.tanya.finhelp.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val vm: RegistrationVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        bindUI()
        observeVM()

        return binding.root
    }

    private fun bindUI() {
        binding.createButton.setOnClickListener {
            vm.signUp(
                email = binding.emailEdit.text.toString(),
                password = binding.passwordEdit.text.toString(),
                username = binding.usernameEdit.text.toString()
            )
        }

        binding.hasAccount.setOnClickListener {
            findNavController().navigate(R.id.to_authFragment)
        }
    }

    private fun observeVM() {
        vm.isSignUp.observe(viewLifecycleOwner) { isSignUp ->
            if (isSignUp) findNavController().navigate(R.id.to_authFragment)
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
