package com.example.bosta.screens.userProfile.userAlbums.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.bosta.screens.userProfile.UserProfileFragmentDirections
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum

class AlbumItemBindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("sendDataToDetailsFragment")
        fun sendDataToDetailsFragment(view: View, item: UserAlbum) {
            view.setOnClickListener {
                val action = UserProfileFragmentDirections.actionUserProfileFragmentToAlbumDetailsFragment(item)
                view.findNavController().navigate(action)
            }
        }
    }
}