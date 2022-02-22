package com.dicoding.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity

@Dao
interface CatalogueDao {
    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getCatalogue(query: SupportSQLiteQuery): DataSource.Factory<Int, CatalogueEntity>

    @Query("select * from movieEntity where id= :id")
    fun getMovieDetail(id: Int): LiveData<MovieEntity>

    @Query("select * from tvEntity where id= :id")
    fun getTvDetail(id: Int): LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatalogue(catalogue: List<CatalogueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvDetail(tv: TvEntity)

    @RawQuery(observedEntities = [FavoriteEntity::class])
    fun getFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FavoriteEntity>

    @Insert
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)

    @Query("select exists(select * from favoriteEntity where id= :id)")
    fun checkFavorite(id: Int): LiveData<Boolean>
}