package com.example.booksearchapp.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    @SerializedName("title") val title: String? = null,
    @SerializedName("author_name") val authorName: List<String>? = null,
    @SerializedName("cover_i") val coverId: Int? = null,
    @SerializedName("publish_year") val publishingYear: List<Int>? = null
) : Parcelable