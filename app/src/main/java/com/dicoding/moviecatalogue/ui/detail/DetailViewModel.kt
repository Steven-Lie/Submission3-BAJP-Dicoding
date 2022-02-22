package com.dicoding.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> =
        catalogueRepository.getMovieDetail(movieId)

    fun getTvDetail(tvId: Int): LiveData<Resource<TvEntity>> = catalogueRepository.getTvDetail(tvId)

    fun setFavorite(favorite: FavoriteEntity) = catalogueRepository.setFavorite(favorite)

    fun checkFavorite(id: Int): LiveData<Boolean> = catalogueRepository.checkFavorite(id)

    fun deleteFavorite(favorite: FavoriteEntity) = catalogueRepository.deleteFavorite(favorite)
}