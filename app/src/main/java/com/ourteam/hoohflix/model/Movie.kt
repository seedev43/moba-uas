package com.ourteam.hoohflix.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int?,
    val results: List<MovieItem>,
    val total_pages: Int?,
    val total_results: Int?
)

data class MovieItem(
    val id: Int,
    val title: String,
    val overview: String,
    val genre: String?,
    val poster_local: Int,
    @SerializedName("poster_path")
    val poster_url: String?,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)
