package com.example.laboratorio12

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.laboratorio12.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment: Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        binding.buttonLogin.setOnClickListener {
            loginUser(
                email = binding.inputLayoutEmail.editText!!.text.toString(),
                password = binding.inputLayoutPassword.editText!!.text.toString()
            )
        }
    }

    private fun loginUser(email: String, password: String) {
        binding.buttonLogin.visibility = View.GONE
        binding.ProgressBarLoginFragment.visibility = View.VISIBLE
        if ((email == getString(R.string.email)) && email == password) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
                navigateToListScreen()
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.error_email_password), Toast.LENGTH_LONG).show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
                binding.buttonLogin.visibility = View.VISIBLE
                binding.ProgressBarLoginFragment.visibility = View.GONE
            }
        }
    }

    private fun navigateToListScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            requireView().findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
    }
}
