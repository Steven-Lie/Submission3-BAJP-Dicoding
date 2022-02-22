package com.dicoding.moviecatalogue.utils

import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.data.api.response.*
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity

object FakeData {
    fun generateMovie(): List<CatalogueEntity> {
        val catalogues = ArrayList<CatalogueEntity>()

        catalogues.add(
            CatalogueEntity(
                634649,
                "Spider-Man: No Way Home",
                8.5,
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "movie",
            )
        )
        return catalogues
    }

    fun generateTv(): List<CatalogueEntity> {
        val catalogues = ArrayList<CatalogueEntity>()

        catalogues.add(
            CatalogueEntity(
                85552,
                "Euphoria",
                8.4,
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
                "tv"
            )
        )
        return catalogues
    }

    fun generateMovieDetail(): MovieEntity = MovieEntity(
        634649,
        "Spider-Man: No Way Home",
        "2021-12-15",
        "Action, Adventure, Science Fiction",
        "148 minutes",
        "The Multiverse unleashed.",
        "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        8.5,
        "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg"
    )

    fun generateTvDetail(): TvEntity = TvEntity(
        85552,
        "Euphoria",
        "2019-06-16 - 2022-01-23",
        "Drama",
        "60 minutes",
        "Remember this feeling.",
        14,
        2,
        "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
        8.4,
        "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
        "/oKt4J3TFjWirVwBqoHyIvv5IImd.jpg"
    )

    fun generateRemoteMovie(): List<MovieResultsItem> {
        val catalogues = ArrayList<MovieResultsItem>()

        catalogues.add(
            MovieResultsItem(
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "Spider-Man: No Way Home",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                8.5,
                634649
            )
        )
        return catalogues
    }

    fun generateRemoteTv(): List<TvResultsItem> {
        val catalogues = ArrayList<TvResultsItem>()

        catalogues.add(
            TvResultsItem(
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                "Euphoria",
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
                8.4,
                85552
            )
        )
        return catalogues
    }

    fun generateRemoteMovieDetail(): MovieDetailResponse {
        val genres = ArrayList<GenresItem>()
        genres.add(GenresItem("Action"))
        genres.add(GenresItem("Adventure"))
        genres.add(GenresItem("Science Fiction"))

        return MovieDetailResponse(
            634649,
            "Spider-Man: No Way Home",
            "2021-12-15",
            genres,
            148,
            "The Multiverse unleashed.",
            "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            8.5,
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg"
        )
    }

    fun generateRemoteTvDetail(): TvDetailResponse {
        val genres = ArrayList<GenresItem>()
        genres.add(GenresItem("Drama"))

        val runtime = listOf(60)

        return TvDetailResponse(
            85552,
            "Euphoria",
            "2019-06-16",
            "2022-01-23",
            genres,
            runtime,
            "Remember this feeling.",
            14,
            2,
            "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
            8.4,
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            "/oKt4J3TFjWirVwBqoHyIvv5IImd.jpg"
        )
    }

    fun generateFavoriteMovie(): List<FavoriteEntity> {
        val catalogues = ArrayList<FavoriteEntity>()

        catalogues.add(
            FavoriteEntity(
                634649,
                "Spider-Man: No Way Home",
                8.5,
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                "movie",
            )
        )
        return catalogues
    }

    fun generateFavoriteTv(): List<FavoriteEntity> {
        val catalogues = ArrayList<FavoriteEntity>()

        catalogues.add(
            FavoriteEntity(
                85552,
                "Euphoria",
                8.4,
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
                "tv"
            )
        )
        return catalogues
    }
}