package com.dicoding.moviecatalogue.data.api.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,
)

data class TvDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("last_air_date")
    val lastAirDate: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,

    @field:SerializedName("tagline")
    val tagline: String,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String
)

data class GenresItem(
    @field:SerializedName("name")
    val name: String,
)