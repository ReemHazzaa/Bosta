package com.example.bosta.screens.userProfile.userAlbums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bosta.databinding.ItemAlbumBinding
import com.example.bosta.screens.userProfile.userAlbums.dataStructures.UserAlbum

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var list = emptyList<UserAlbum>()

    class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserAlbum) {
            binding.album = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): AlbumViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAlbumBinding.inflate(layoutInflater, parent, false)
                return AlbumViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.from(parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val currentItem = list[position]
        if(currentItem.title == null) {
            currentItem.title = "No title!"
        }
        holder.bind(currentItem)
    }

    fun setList(albumsList: List<UserAlbum>) {
        val tasksDiffUtil = AlbumsDiffUtil(list, albumsList)
        val diffUtilResult = DiffUtil.calculateDiff(tasksDiffUtil)
        list = albumsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun getList(): List<UserAlbum> = list
}
