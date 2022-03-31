package com.example.bosta.screens.albumDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bosta.R
import com.example.bosta.common.displayErrorSnackbar
import com.example.bosta.common.makeGone
import com.example.bosta.common.makeVisible
import com.example.bosta.databinding.FragmentAlbumDetailsBinding
import com.example.bosta.remoteDataSource.NetworkResult
import com.example.bosta.screens.albumDetails.adapter.PhotosAdapter
import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum
import com.example.bosta.screens.userProfile.usersList.dataStructures.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumDetailsFragment : Fragment() {
    private val TAG = AlbumDetailsFragment::class.java.simpleName
    private var _binding: FragmentAlbumDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumDetailsFragmentArgs by navArgs()
    private lateinit var album: UserAlbum
    private val photosAdapter by lazy { PhotosAdapter() }
    private lateinit var albumDetailsViewModel: AlbumDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        album = args.currentAlbum
        albumDetailsViewModel = ViewModelProvider(requireActivity())[AlbumDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this

        binding.refreshLayout.setOnRefreshListener {
            requestPhotosAPI()
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                photosAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })


        setUpRecyclerView()
        requestPhotosAPI()

        return binding.root
    }

    private fun requestPhotosAPI() {
        album.id?.let { albumDetailsViewModel.getAlbumPhotos(albumID = it) }
        albumDetailsViewModel.photosResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideLoading()
                    try {
                        response.data.let {
                            @Suppress("UNCHECKED_CAST")
                            photosAdapter.setList(it as List<AlbumPhoto>)
                            photosAdapter.setListAll(it)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is NetworkResult.Error -> {
                    hideLoading()
                    displayErrorSnackbar(getString(R.string.no_photos), requireActivity())
                }
                is NetworkResult.Loading -> showLoading()
            }

        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            binding.albumsRV.apply {
                adapter = photosAdapter
                layoutManager = GridLayoutManager(requireContext(), 4).also {
                    setPadding(1,1,1,1)
                }
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