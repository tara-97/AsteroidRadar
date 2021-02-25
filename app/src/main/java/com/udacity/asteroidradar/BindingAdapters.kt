package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get()
                .load(imgUrl)
                .into(imgView)
    }
}
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}


@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
@BindingAdapter("asteroidContentDescription")
fun bindContextDescriptionToImage(imageView: ImageView,isHazardous: Boolean){
    if (isHazardous) {
        imageView.contentDescription = R.string.potentially_hazardous_asteroid_image.toString()
    } else {
        imageView.contentDescription = R.string.not_hazardous_asteroid_image.toString()
    }
}
@BindingAdapter("nasaPictureOfDay")
fun bindNasaPictureToImage(imageView: ImageView,imgTitle: String?){
    val context = imageView.context
    if (imgTitle == null) {
        imageView.contentDescription = R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet.toString()
    } else {
        imageView.contentDescription = String.format(context.getString(R.string.nasa_picture_of_day_content_description_format),imgTitle)
    }
}


