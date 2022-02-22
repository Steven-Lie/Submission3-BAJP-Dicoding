package com.dicoding.moviecatalogue.di

import android.content.Context
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository
import com.dicoding.moviecatalogue.data.api.RemoteDataSource
import com.dicoding.moviecatalogue.data.local.LocalDataSource
import com.dicoding.moviecatalogue.data.local.room.CatalogueDatabase
import com.dicoding.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): CatalogueRepository {
        val database = CatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        val appExecutors = AppExecutors()

        return CatalogueRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}