package com.dicoding.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository

class MovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovies(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>> =
        catalogueRepository.getMovie(sort)

    fun getFavoriteMovies(sort: String): LiveData<PagedList<FavoriteEntity>> =
        catalogueRepository.getFavoriteMovie(sort)
}