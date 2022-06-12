package com.example.scaler.util

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.scaler.R
import com.example.scaler.constants.APIConstants
import com.example.scaler.extensions.loadImage

object BaseBindingAdapter {

    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(view: ImageView?, url: String?) {
        view?.loadImage(url, R.drawable.ic_placeholder_image)
    }

    @BindingAdapter("loadThumbnailImage")
    @JvmStatic
    fun loadThumbnailImage(view: ImageView?, url: String?) {
        view?.loadImage(APIConstants.URL_IMAGE_PREFIX + url, R.drawable.ic_placeholder_image)
    }
//    @BindingAdapter("changeBGColor")
//    @JvmStatic
//    fun loadThumbnailImage(clMainVideoView:ConstraintLayout) {
//
//    }
}
