package com.dicoding.moviecatalogue.data.api

import com.dicoding.moviecatalogue.data.api.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun loadMovie(
        @Query("api_key") api_key: String
    ): Call<MovieResponse>

    @GET("movie/{movieId}")
    fun loadMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") api_key: String
    ): Call<MovieDetailResponse>

    @GET("tv/popular")
    fun loadTv(
        @Query("api_key") api_key: String
    ): Call<TvResponse>

    @GET("tv/{tvId}")
    fun loadTVDetail(
        @Path("tvId") tvId: Int,
        @Query("api_key") api_key: String
    ): Call<TvDetailResponse>
}