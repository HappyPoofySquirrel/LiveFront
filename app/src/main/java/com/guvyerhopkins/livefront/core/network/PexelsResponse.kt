package com.guvyerhopkins.livefront.core.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PexelsResponse(
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("photos")
    val photos: List<Photo> = listOf(),
    @SerializedName("total_results")
    val totalResults: Int?
)

@Parcelize
data class Photo(
    @SerializedName("avg_color")
    val avgColor: String?,
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("liked")
    val liked: Boolean = false,
    @SerializedName("photographer")
    val photographer: String?,
    @SerializedName("photographer_id")
    val photographerId: Int?,
    @SerializedName("photographer_url")
    val photographerUrl: String?,
    @SerializedName("src")
    val src: Src?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
) : Parcelable

@Parcelize
data class Src(
    @SerializedName("landscape")
    val landscape: String?,
    @SerializedName("large")
    val large: String?,
    @SerializedName("large2x")
    val large2x: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("original")
    val original: String?,
    @SerializedName("portrait")
    val portrait: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("tiny")
    val tiny: String?
) : Parcelable