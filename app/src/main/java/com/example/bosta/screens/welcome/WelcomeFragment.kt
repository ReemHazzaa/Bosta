package com.example.bosta.screens.welcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bosta.R
import com.example.bosta.common.displayErrorSnackbar
import com.example.bosta.common.makeGone
import com.example.bosta.common.makeVisible
import com.example.bosta.databinding.FragmentWelcomeBinding
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {
    private val TAG = WelcomeFragment::class.java.simpleName
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        welcomeViewModel = ViewModelProvider(requireActivity())[WelcomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requestUsersAPI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun requestUsersAPI() {
        welcomeViewModel.getUsers()

        welcomeViewModel.usersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    try {
                        response.data.let {
                            // Get random number between 1:10
                            val randomUserNo = (0..10).random()
                            Log.i(TAG, "Chosen user no $randomUserNo")
                            val user = it?.get(randomUserNo)
                            if (user != null) {
                                Log.i(TAG, "User: [ID=${user.id}] [Name=${user.name}]")
                                navigateToProfile(user)
                            } else {
                                Log.i(TAG, "User is null!")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is NetworkResult.Error -> {
                    lifecycleScope.launch(Dispatchers.Main.immediate) {
                        displayErrorSnackbar(getString(R.string.no_users), requireActivity())
                    }
                }
                is NetworkResult.Loading -> {  }
            }

        }
    }

    private fun navigateToProfile(user: User) {
        Log.e(TAG, "Navigating to user profile")
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToUserProfileFragment(user)
        findNavController().navigate(action)
    }
}