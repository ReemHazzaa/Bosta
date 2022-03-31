package com.example.bosta.screens.albumDetails.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum

class PhotosDiffUtil(
    private val oldList: List<AlbumPhoto>,
    private val newList: List<AlbumPhoto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].albumId == newList[newItemPosition].albumId
                && oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].url == newList[newItemPosition].url
                && oldList[oldItemPosition].thumbnailUrl == newList[newItemPosition].thumbnailUrl
    }
}