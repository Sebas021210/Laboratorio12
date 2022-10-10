package com.example.laboratorio12

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import com.example.laboratorio12.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.triggerStateFlow()

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.validAuthToken.collectLatest {
                status(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            homeViewModel.uiState.collectLatest {
                viewStatus(it)
            }
        }
    }

    private fun status(it: Boolean) {
        when (it) {
            true -> {
            }
            false -> {
                requireView().findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
        }
    }

    private fun viewStatus(it: HomeViewModel.UiState) {
        when (it){
            is HomeViewModel.UiState.Default ->{
                binding.apply {
                    textView.text = getString(R.string.textDefault)
                    imageViewHomeFragment.load(R.drawable.ic_android_black_24dp)
                    imageViewHomeFragment.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.UiState.Success ->{
                binding.apply {
                    textView.text = getString(R.string.textSuccess)
                    imageViewHomeFragment.load(R.drawable.ic_baseline_check_circle_outline_24)
                    imageViewHomeFragment.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.UiState.Failure ->{
                binding.apply {
                    textView.text = getString(R.string.textFailure)
                    imageViewHomeFragment.load(R.drawable.ic_baseline_sms_failed_24)
                    imageViewHomeFragment.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            is HomeViewModel.UiState.Empty ->{
                binding.apply {
                    textView.text = getString(R.string.textEmpty)
                    imageViewHomeFragment.load(R.drawable.ic_baseline_do_disturb_alt_24)
                    imageViewHomeFragment.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
            }
            is HomeViewModel.UiState.Loading ->{
                binding.apply {
                    imageViewHomeFragment.visibility = View.GONE
                    textView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonDefault.setOnClickListener {
            homeViewModel.default()
        }
        binding.buttonSuccess.setOnClickListener {
            homeViewModel.success()
        }
        binding.buttonFailure.setOnClickListener {
            homeViewModel.failure()
        }
        binding.buttonEmpty.setOnClickListener {
            homeViewModel.empty()
        }
        binding.buttonCerrasSesion.setOnClickListener {
            mainViewModel.logout()

        }
        binding.buttonSesionActiva.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }
    }
}