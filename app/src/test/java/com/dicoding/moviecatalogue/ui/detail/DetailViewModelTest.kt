package com.dicoding.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository
import com.dicoding.moviecatalogue.utils.FakeData
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
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovies = FakeData.generateMovieDetail()
    private val dummyTvs = FakeData.generateTvDetail()
    private val movieId = dummyMovies.id as Int
    private val tvId = dummyTvs.id as Int
    private val wrongMovieId = 1

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvEntity>>

    @Before
    fun setup() {
        viewModel = DetailViewModel(catalogueRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = Resource.success(FakeData.generateMovieDetail())
        val movieData = MutableLiveData<Resource<MovieEntity>>()
        movieData.value = dummyMovie

        `when`(catalogueRepository.getMovieDetail(movieId)).thenReturn(movieData)
        val movieEntity = viewModel.getMovieDetail(movieId).value as Resource<MovieEntity>
        verify(catalogueRepository).getMovieDetail(movieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovies.id, movieEntity.data?.id)
        assertEquals(dummyMovies.genres, movieEntity.data?.genres)
        assertEquals(dummyMovies.releaseDate, movieEntity.data?.releaseDate)
        assertEquals(dummyMovies.backdrop, movieEntity.data?.backdrop)
        assertEquals(dummyMovies.poster, movieEntity.data?.poster)
        assertEquals(dummyMovies.tagline, movieEntity.data?.tagline)
        assertEquals(dummyMovies.runtime, movieEntity.data?.runtime)
        assertEquals(dummyMovies.overview, movieEntity.data?.overview)
        assertEquals(dummyMovies.rating, movieEntity.data?.rating)

        viewModel.getMovieDetail(movieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTv() {
        val dummyTv = Resource.success(FakeData.generateTvDetail())
        val tvData = MutableLiveData<Resource<TvEntity>>()
        tvData.value = dummyTv

        `when`(catalogueRepository.getTvDetail(tvId)).thenReturn(tvData)
        val tvEntity = viewModel.getTvDetail(tvId).value as Resource<TvEntity>
        verify(catalogueRepository).getTvDetail(tvId)
        assertNotNull(tvEntity)
        assertEquals(dummyTvs.id, tvEntity.data?.id)
        assertEquals(dummyTvs.season, tvEntity.data?.season)
        assertEquals(dummyTvs.episode, tvEntity.data?.episode)
        assertEquals(dummyTvs.backdrop, tvEntity.data?.backdrop)
        assertEquals(dummyTvs.genres, tvEntity.data?.genres)
        assertEquals(dummyTvs.overview, tvEntity.data?.overview)
        assertEquals(dummyTvs.poster, tvEntity.data?.poster)
        assertEquals(dummyTvs.rating, tvEntity.data?.rating)
        assertEquals(dummyTvs.releaseDate, tvEntity.data?.releaseDate)
        assertEquals(dummyTvs.runtime, tvEntity.data?.runtime)
        assertEquals(dummyTvs.tagline, tvEntity.data?.tagline)
        assertEquals(dummyTvs.title, tvEntity.data?.title)

        viewModel.getTvDetail(tvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTv)
    }

    @Test
    fun emptyDetail() {
        val movieData = MutableLiveData<Resource<MovieEntity>>()

        `when`(catalogueRepository.getMovieDetail(wrongMovieId)).thenReturn(movieData)
        val movieEntity = viewModel.getMovieDetail(wrongMovieId).value
        verify(catalogueRepository).getMovieDetail(wrongMovieId)
        assertNull(movieEntity)
    }
}