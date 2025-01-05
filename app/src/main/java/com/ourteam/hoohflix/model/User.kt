package com.ourteam.hoohflix.model

import com.google.gson.annotations.SerializedName

data class ResponseBody(
    val success: Boolean,
    val message: String,
    val data: Any?
)

data class RecommendationMovieResponse(
    val success: Boolean,
    val message: String,
    val data: List<MovieItem>
)

data class RecommendationRequest(
    val user_id: Int
)

data class RecommendMovie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val genres: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val id: Int,
    val username: String,
    val message: String
)

data class RegisterRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val username: String,
    val email: String,
    val password: String
)

data class UserDetailResponse(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String
)

data class SubmitRatingRequest(
    val user_id: Int,
    val movie_id: String,
    val rating: Int,
    val genre: String
)