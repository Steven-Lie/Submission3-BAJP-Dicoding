package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository

class TvViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getTvs(sort: String): LiveData<Resource<PagedList<CatalogueEntity>>> =
        catalogueRepository.getTv(sort)

    fun getFavoriteTvs(sort: String): LiveData<PagedList<FavoriteEntity>> =
        catalogueRepository.getFavoriteTv(sort)
}