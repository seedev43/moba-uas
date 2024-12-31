package com.ourteam.hoohflix.api

import com.ourteam.hoohflix.model.LoginRequest
import com.ourteam.hoohflix.model.MovieResponse
import com.ourteam.hoohflix.model.MovieDetail
import com.ourteam.hoohflix.model.RegisterRequest
import com.ourteam.hoohflix.model.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 2
    ): MovieResponse

    @GET("movie/{id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ): MovieDetail

    @GET("search/movie?language=en-US")
    suspend fun searchMovie(
        @Query("query") query: String = "doraemon",
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int = 1
    ): MovieResponse

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<ResponseBody>

    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<ResponseBody>
}