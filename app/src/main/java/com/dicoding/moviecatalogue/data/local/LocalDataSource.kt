package com.dicoding.moviecatalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.data.local.room.CatalogueDao
import com.dicoding.moviecatalogue.utils.SortUtils

class LocalDataSource private constructor(private val mCatalogueDao: CatalogueDao) {
    fun getCatalogue(sort: String, type: String): DataSource.Factory<Int, CatalogueEntity> {
        val query = SortUtils.getSortedQuery(sort, type)
        return mCatalogueDao.getCatalogue(query)
    }

    fun getMovieDetails(id: Int): LiveData<MovieEntity> = mCatalogueDao.getMovieDetail(id)

    fun getTvDetails(id: Int): LiveData<TvEntity> = mCatalogueDao.getTvDetail(id)

    fun insertCatalogue(catalogues: List<CatalogueEntity>) =
        mCatalogueDao.insertCatalogue(catalogues)

    fun insertMovieDetail(movie: MovieEntity) = mCatalogueDao.insertMovieDetail(movie)

    fun insertTvDetail(tv: TvEntity) = mCatalogueDao.insertTvDetail(tv)

    fun getFavorite(sort: String, type: String): DataSource.Factory<Int, FavoriteEntity> {
        val query = SortUtils.getSortedFavoriteQuery(sort, type)
        return mCatalogueDao.getFavorite(query)
    }

    fun setFavorite(favorite: FavoriteEntity) = mCatalogueDao.insertFavorite(favorite)

    fun deleteFavorite(favorite: FavoriteEntity) = mCatalogueDao.deleteFavorite(favorite)

    fun checkFavorite(id: Int): LiveData<Boolean> = mCatalogueDao.checkFavorite(id)

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(catalogueDao).apply {
                    INSTANCE = this
                }
            }
    }
}