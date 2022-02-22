package com.dicoding.moviecatalogue.ui.movie

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
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

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
        viewModel = MovieViewModel(catalogueRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = Resource.success(cataloguePagedList)
        `when`(dummyMovie.data?.size).thenReturn(20)
        val movie = MutableLiveData<Resource<PagedList<CatalogueEntity>>>()
        movie.value = dummyMovie

        `when`(catalogueRepository.getMovie(SortUtils.POPULAR)).thenReturn(movie)
        val catalogueEntities = viewModel.getMovies(SortUtils.POPULAR).value?.data
        verify(catalogueRepository).getMovie(SortUtils.POPULAR)
        assertNotNull(catalogueEntities)
        assertEquals(20, catalogueEntities?.size)

        viewModel.getMovies(SortUtils.POPULAR).observeForever(catalogueObserver)
        verify(catalogueObserver).onChanged(dummyMovie)
    }

    @Test
    fun getFavorites() {
        val dummyFavorite = favoritePagedList
        `when`(dummyFavorite.size).thenReturn(5)
        val favorite = MutableLiveData<PagedList<FavoriteEntity>>()
        favorite.value = dummyFavorite

        `when`(catalogueRepository.getFavoriteMovie(SortUtils.POPULAR)).thenReturn(favorite)
        val favoriteEntities = viewModel.getFavoriteMovies(SortUtils.POPULAR).value
        verify(catalogueRepository).getFavoriteMovie(SortUtils.POPULAR)
        assertNotNull(favoriteEntities)
        assertEquals(5, favoriteEntities?.size)

        viewModel.getFavoriteMovies(SortUtils.POPULAR).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyFavorite)
    }
}