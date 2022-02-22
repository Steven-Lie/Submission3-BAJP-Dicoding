package com.dicoding.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository
import com.dicoding.moviecatalogue.utils.SortUtils
import com.dicoding.moviecatalogue.valueobject.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var catalogueObserver: Observer<Resource<PagedList<CatalogueEntity>>>

    @Mock
    private lateinit var cataloguePagedList: PagedList<CatalogueEntity>

    @Mock
    private lateinit var favoriteObserver: Observer<PagedList<FavoriteEntity>>

    @Mock
    private lateinit var favoritePagedList: PagedList<FavoriteEntity>

    @Before
    fun setup() {
        viewModel = TvViewModel(catalogueRepository)
    }

    @Test
    fun getTvs() {
        val dummyTv = Resource.success(cataloguePagedList)
        `when`(dummyTv.data?.size).thenReturn(20)
        val tv = MutableLiveData<Resource<PagedList<CatalogueEntity>>>()
        tv.value = dummyTv

        `when`(catalogueRepository.getTv(SortUtils.POPULAR)).thenReturn(tv)
        val catalogueEntities = viewModel.getTvs(SortUtils.POPULAR).value?.data
        verify(catalogueRepository).getTv(SortUtils.POPULAR)
        assertNotNull(catalogueEntities)
        assertEquals(20, catalogueEntities?.size)

        viewModel.getTvs(SortUtils.POPULAR).observeForever(catalogueObserver)
        verify(catalogueObserver).onChanged(dummyTv)
    }

    @Test
    fun getFavorites() {
        val dummyFavorite = favoritePagedList
        `when`(dummyFavorite.size).thenReturn(5)
        val favorite = MutableLiveData<PagedList<FavoriteEntity>>()
        favorite.value = dummyFavorite

        `when`(catalogueRepository.getFavoriteTv(SortUtils.POPULAR)).thenReturn(favorite)
        val favoriteEntities = viewModel.getFavoriteTvs(SortUtils.POPULAR).value
        verify(catalogueRepository).getFavoriteTv(SortUtils.POPULAR)
        assertNotNull(favoriteEntities)
        assertEquals(5, favoriteEntities?.size)

        viewModel.getFavoriteTvs(SortUtils.POPULAR).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyFavorite)
    }
}