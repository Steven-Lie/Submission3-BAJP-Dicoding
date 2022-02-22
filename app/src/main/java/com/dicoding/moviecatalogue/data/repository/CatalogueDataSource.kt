package com.dicoding.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity

interface CatalogueDataSource {
    fun getMovie(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getTv(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>>

    fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>>

    fun getFavoriteMovie(sort: String): LiveData<PagedList<FavoriteEntity>>

    fun getFavoriteTv(sort: String): LiveData<PagedList<FavoriteEntity>>

    fun setFavorite(favorite: FavoriteEntity)

    fun deleteFavorite(favorite: FavoriteEntity)

    fun checkFavorite(id: Int): LiveData<Boolean>
}