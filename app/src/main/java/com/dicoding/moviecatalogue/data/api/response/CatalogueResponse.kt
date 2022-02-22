package com.dicoding.moviecatalogue.data.api.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("results")
    val results: List<MovieResultsItem>
)

data class MovieResultsItem(
    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int,
)

data class TvResponse(
    @field:SerializedName("results")
    val results: List<TvResultsItem>
)

data class TvResultsItem(
    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("name")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int
)
