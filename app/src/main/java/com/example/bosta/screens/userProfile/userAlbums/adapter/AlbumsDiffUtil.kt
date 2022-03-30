package com.example.bosta.screens.userProfile.userAlbums.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum

class AlbumsDiffUtil(
    private val oldList: List<UserAlbum>,
    private val newList: List<UserAlbum>
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
        return oldList[oldItemPosition].userId == newList[newItemPosition].userId
                && oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}