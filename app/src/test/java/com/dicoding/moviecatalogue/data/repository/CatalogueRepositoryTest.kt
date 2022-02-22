package com.dicoding.moviecatalogue.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.moviecatalogue.data.api.RemoteDataSource
import com.dicoding.moviecatalogue.data.local.LocalDataSource
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.data.local.entity.MovieEntity
import com.dicoding.moviecatalogue.data.local.entity.TvEntity
import com.dicoding.moviecatalogue.utils.*
import com.dicoding.moviecatalogue.valueobject.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CatalogueRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val catalogueRepository = FakeCatalogueRepository(remote, local, appExecutors)

    private val movieResponse = FakeData.generateRemoteMovie()
    private val movieFavoriteResponse = FakeData.generateFavoriteMovie()
    private val movieDetailResponse = FakeData.generateRemoteMovieDetail()
    private val movieId = movieDetailResponse.id
    private val tvResponse = FakeData.generateRemoteTv()
    private val tvFavoriteResponse = FakeData.generateFavoriteTv()
    private val tvDetailResponse = FakeData.generateRemoteTvDetail()
    private val tvId = tvDetailResponse.id

    @Test
    fun getMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CatalogueEntity>
        `when`(local.getCatalogue(SortUtils.POPULAR, "movie")).thenReturn(dataSourceFactory)
        catalogueRepository.getMovie(SortUtils.POPULAR)

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(FakeData.generateMovie()))
        verify(local).getCatalogue(SortUtils.POPULAR, "movie")
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = FakeData.generateMovieDetail()
        `when`(local.getMovieDetails(movieId)).thenReturn(dummyMovie)

        val movieDetailEntity =
            LiveDataTestUtils.getValue(catalogueRepository.getMovieDetail(movieId))
        verify(local).getMovieDetails(movieId)
        assertNotNull(movieDetailEntity.data)
        assertEquals(movieDetailEntity.data?.title, movieDetailResponse.title)
        assertEquals(movieDetailEntity.data?.overview, movieDetailResponse.overview)
    }

    @Test
    fun getTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CatalogueEntity>
        `when`(local.getCatalogue(SortUtils.POPULAR, "tv")).thenReturn(dataSourceFactory)
        catalogueRepository.getTv(SortUtils.POPULAR)

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(FakeData.generateTv()))
        verify(local).getCatalogue(SortUtils.POPULAR, "tv")
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getTvDetail() {
        val dummyTv = MutableLiveData<TvEntity>()
        dummyTv.value = FakeData.generateTvDetail()
        `when`(local.getTvDetails(tvId)).thenReturn(dummyTv)

        val tvDetailEntity = LiveDataTestUtils.getValue(catalogueRepository.getTvDetail(tvId))
        verify(local).getTvDetails(tvId)
        assertNotNull(tvDetailEntity.data)
        assertEquals(tvDetailEntity.data?.title, tvDetailResponse.name)
        assertEquals(tvDetailEntity.data?.overview, tvDetailResponse.overview)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getFavorite(SortUtils.POPULAR, "movie")).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteMovie(SortUtils.POPULAR)

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(FakeData.generateFavoriteMovie()))
        verify(local).getFavorite(SortUtils.POPULAR, "movie")
        assertNotNull(movieEntities.data)
        assertEquals(movieFavoriteResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(local.getFavorite(SortUtils.POPULAR, "tv")).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteTv(SortUtils.POPULAR)

        val tvEntities =
            Resource.success(PagedListUtil.mockPagedList(FakeData.generateFavoriteTv()))
        verify(local).getFavorite(SortUtils.POPULAR, "tv")
        assertNotNull(tvEntities.data)
        assertEquals(tvFavoriteResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }
}