package com.example.bosta.screens.userProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bosta.R
import com.example.bosta.common.displayErrorSnackbar
import com.example.bosta.common.makeGone
import com.example.bosta.common.makeVisible
import com.example.bosta.databinding.FragmentUserProfileBinding
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.screens.userProfile.userAlbums.UserAlbumsViewModel
import com.example.bosta.screens.userProfile.userAlbums.adapter.AlbumsAdapter
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class UserProfileFragment : Fragment() {
    private val TAG = UserProfileFragment::class.java.simpleName
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val args: UserProfileFragmentArgs by navArgs()
    private lateinit var user: User
    private val albumsAdapter by lazy { AlbumsAdapter() }
    private lateinit var userAlbumsViewModel: UserAlbumsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = args.user
        userAlbumsViewModel = ViewModelProvider(requireActivity())[UserAlbumsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this

        binding.apply {
            userNameTV.text = user.name
            userAddressTV.text = StringBuilder().append(user.address?.street).append(",")
                .append(user.address?.city).append(",")
                .append(user.address?.suite).toString()
            userPhoneTV.text = user.phone

            refreshLayout.setOnRefreshListener {
                requestAlbumsAPI()
            }
        }

        setUpRecyclerView()
        requestAlbumsAPI()

        return binding.root
    }

    private fun requestAlbumsAPI() {
        user.id?.let { userAlbumsViewModel.getAlbums(userID = it) }
        userAlbumsViewModel.usersResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideLoading()
                    try {
                        response.data.let {
                            @Suppress("UNCHECKED_CAST")
                            albumsAdapter.setList(it as List<UserAlbum>)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is NetworkResult.Error -> {
                    hideLoading()
                    displayErrorSnackbar(getString(R.string.no_albums), requireActivity())
                }
                is NetworkResult.Loading -> showLoading()
            }

        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            binding.albumsRV.apply {
                adapter = albumsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        showLoading()
    }

    private fun showLoading() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            binding.apply {
                albumsRV.makeGone()
                loading.makeVisible()
            }
        }
    }

    private fun hideLoading() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            binding.apply {
                albumsRV.makeVisible()
                loading.makeGone()
                refreshLayout.isRefreshing = false
            }
        }
    }
}