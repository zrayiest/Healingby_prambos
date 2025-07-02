package com.jimi15.uas_nmp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Location (val id_location: Int, val name: String, val image_url: String,
                val short_description: String, val category: String,  val full_description: String,
                val address: String, val operating_hours: String)