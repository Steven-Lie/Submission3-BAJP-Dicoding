package com.dicoding.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.data.NetworkBoundResource
import com.dicoding.moviecatalogue.data.api.ApiResponse
import com.dicoding.moviecatalogue.data.api.RemoteDataSource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.data.api.response.MovieDetailResponse
import com.dicoding.moviecatalogue.data.api.response.MovieResultsItem
import com.dicoding.moviecatalogue.data.api.response.TvDetailResponse
import com.dicoding.moviecatalogue.data.api.response.TvResultsItem
import com.dicoding.moviecatalogue.data.local.LocalDataSource
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.utils.AppExecutors

class FakeCatalogueRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : CatalogueDataSource {
    override fun getMovie(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>> {
        return object :
            NetworkBoundResource<PagedList<CatalogueEntity>, List<MovieResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<CatalogueEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getCatalogue(sort, "movie"),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<CatalogueEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResultsItem>>> =
                remoteDataSource.getMovie()

            override fun saveCallResult(data: List<MovieResultsItem>) {
                val movieList = ArrayList<CatalogueEntity>()
                for (response in data) {
                    val movie = CatalogueEntity(
                        response.id,
                        response.title,
                        response.voteAverage,
                        response.overview,
                        response.posterPath,
                        "movie"
                    )
                    movieList.add(movie)
                }
                localDataSource.insertCatalogue(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieDetails(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {
                val movie = MovieEntity(
                    data.id,
                    data.title,
                    data.releaseDate,
                    data.genres.joinToString(
                        separator = ", ",
                        transform = { it.name }),
                    "${data.runtime} minutes",
                    data.tagline,
                    data.overview,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
                localDataSource.insertMovieDetail(movie)
            }
        }.asLiveData()
    }

    override fun getTv(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>> {
        return object :
            NetworkBoundResource<PagedList<CatalogueEntity>, List<TvResultsItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<CatalogueEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    localDataSource.getCatalogue(sort, "tv"),
                    config
                ).build()
            }

            override fun shouldFetch(data: PagedList<CatalogueEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvResultsItem>>> =
                remoteDataSource.getTv()

            override fun saveCallResult(data: List<TvResultsItem>) {
                val tvList = ArrayList<CatalogueEntity>()
                for (response in data) {
                    val tv = CatalogueEntity(
                        response.id,
                        response.title,
                        response.voteAverage,
                        response.overview,
                        response.posterPath,
                        "tv"
                    )
                    tvList.add(tv)
                }
                localDataSource.insertCatalogue(tvList)
            }
        }.asLiveData()
    }

    override fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvEntity> =
                localDataSource.getTvDetails(tvId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse>> =
                remoteDataSource.getTvDetail(tvId)

            override fun saveCallResult(data: TvDetailResponse) {
                val tv = TvEntity(
                    data.id,
                    data.name,
                    "${data.firstAirDate} - ${data.lastAirDate}",
                    data.genres.joinToString(
                        separator = ", ",
                        transform = { it.name }),
                    data.episodeRunTime.joinToString(
                        separator = ", ",
                        transform = { "$it minutes" }),
                    data.tagline,
                    data.numberOfEpisodes,
                    data.numberOfSeasons,
                    data.overview,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
                localDataSource.insertTvDetail(tv)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(sort: String): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavorite(sort, "movie"), config).build()
    }

    override fun getFavoriteTv(sort: String): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavorite(sort, "tv"), config).build()
    }

    override fun setFavorite(favorite: FavoriteEntity) =
        appExecutors.diskIO().execute { localDataSource.setFavorite(favorite) }

    override fun deleteFavorite(favorite: FavoriteEntity) =
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(favorite) }

    override fun checkFavorite(id: Int): LiveData<Boolean> =
        localDataSource.checkFavorite(id)
}