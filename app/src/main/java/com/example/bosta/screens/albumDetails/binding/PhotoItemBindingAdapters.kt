package com.example.bosta.screens.albumDetails.binding

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.bosta.R

class PhotoItemBindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(imageView: ImageView, url: String?) {
            if (url != "" && url != null) {
                Glide.with(imageView.context).load(url).into(imageView)
            } else {
                Glide.with(imageView.context).load(R.drawable.ic_baseline_photo_24).into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(webView: WebView, url: String?) {
            if (url != "" && url != null) {
               webView.loadUrl(url)
                //Glide.with(imageView.context).load(url).into(imageView)
            } else {
                //Glide.with(imageView.context).load(R.drawable.ic_baseline_photo_24).into(imageView)
            }
        }

    }
}