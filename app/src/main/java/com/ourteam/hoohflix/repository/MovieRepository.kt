package com.ourteam.hoohflix.repository

import com.ourteam.hoohflix.api.ApiService
import com.ourteam.hoohflix.data.recentMoviesData
import com.ourteam.hoohflix.model.MovieItem
import com.ourteam.hoohflix.model.MovieResponse
import com.ourteam.hoohflix.model.MovieDetail
import com.ourteam.hoohflix.api.MovieRetrofitClient
import com.ourteam.hoohflix.api.MovieRetrofitClient.movieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository() {
    private val apiService = MovieRetrofitClient.movieService
    fun recentMovies(): List<MovieItem> = recentMoviesData()

//    fun fetchPopularMovies(onSuccess: (List<MovieItem>) -> Unit, onError: (String) -> Unit) {
//        apiService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                if (response.isSuccessful) {
//                    response.body()?.let { movieResponse ->
//                        onSuccess(movieResponse.results)
//                    } ?: onError("Response body is null")
//                } else {
//                    onError("Error: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                onError("Error fetching movies: ${t.message}")
//            }
//        })
//    }
//
//    fun fetchTopRatedMovies(onSuccess: (List<MovieItem>) -> Unit, onError: (String) -> Unit) {
//        apiService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                if (response.isSuccessful) {
//                    response.body()?.let { movieResponse ->
//                        onSuccess(movieResponse.results)
//                    } ?: onError("Response body is null")
//                } else {
//                    onError("Error: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                onError("Error fetching movies: ${t.message}")
//            }
//        })
//    }

//    suspend fun getTopRatedMovie(): MovieResponse {
//        return movieService.getTopRatedMovies()
//    }
//
//    suspend fun getMovieDetail(id: Int): MovieDetail {
//        return movieService.getMovieDetail(id)
//    }
}