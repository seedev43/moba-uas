package com.ourteam.hoohflix.api

import com.ourteam.hoohflix.model.MovieResponse
import com.ourteam.hoohflix.model.MovieDetail
import com.ourteam.hoohflix.model.MovieItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    @GET("popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("{id}?language=en-US")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ): MovieDetail

    @GET("search/movie?language=en-US")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int = 1
    ): MovieResponse
}