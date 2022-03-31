package com.example.bosta.screens.albumDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bosta.databinding.ItemPhotoBinding
import com.example.bosta.screens.albumDetails.dataStructures.AlbumPhoto

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.AlbumViewHolder>(), Filterable {

    private var list = ArrayList<AlbumPhoto>()
    private var listAll = ArrayList<AlbumPhoto>()

    class AlbumViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlbumPhoto) {
            /*
            Glide.with(binding.photoIV.context)
                .load(item.url)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.photoIV)

             */
            item.url?.let { binding.webView.loadUrl(it) }
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): AlbumViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPhotoBinding.inflate(layoutInflater, parent, false)
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
        holder.bind(currentItem)
    }

    fun setList(albumsList: List<AlbumPhoto>) {
        val tasksDiffUtil = PhotosDiffUtil(list, albumsList)
        val diffUtilResult = DiffUtil.calculateDiff(tasksDiffUtil)
        list = albumsList as ArrayList<AlbumPhoto>
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun setListAll(albumsList: List<AlbumPhoto>) {
        listAll = albumsList as ArrayList<AlbumPhoto>
    }

    fun getList(): List<AlbumPhoto> = list

    override fun getFilter(): Filter {
        return myFilter
    }

    private var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<AlbumPhoto> = ArrayList()
            if (charSequence.isEmpty()) {
                filteredList.addAll(listAll)
            } else {
                for (photo in listAll) {
                    if (photo.title.toString().contains(charSequence.toString(), true)) {
                        filteredList.add(photo)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            try {
                setList(filterResults.values as List<AlbumPhoto>)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
