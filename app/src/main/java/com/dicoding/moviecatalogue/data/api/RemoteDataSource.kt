package com.dicoding.moviecatalogue.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.moviecatalogue.BuildConfig
import com.dicoding.moviecatalogue.data.api.response.*
import com.dicoding.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    fun getMovie(): LiveData<ApiResponse<List<MovieResultsItem>>> {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadMovie(BuildConfig.KEY)
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResultsItem>>>()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    resultMovie.value =
                        ApiResponse.success(response.body()?.results as List<MovieResultsItem>)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadMovieDetail(movieId, BuildConfig.KEY)
        val resultMovie = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    resultMovie.value = ApiResponse.success(response.body() as MovieDetailResponse)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getTv(): LiveData<ApiResponse<List<TvResultsItem>>> {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadTv(BuildConfig.KEY)
        val resultTv = MutableLiveData<ApiResponse<List<TvResultsItem>>>()
        client.enqueue(object : Callback<TvResponse> {
            override fun onResponse(
                call: Call<TvResponse>,
                response: Response<TvResponse>
            ) {
                if (response.isSuccessful) {
                    resultTv.value =
                        ApiResponse.success(response.body()?.results as List<TvResultsItem>)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultTv
    }

    fun getTvDetail(tvId: Int): LiveData<ApiResponse<TvDetailResponse>> {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadTVDetail(tvId, BuildConfig.KEY)
        val resultTv = MutableLiveData<ApiResponse<TvDetailResponse>>()
        client.enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(
                call: Call<TvDetailResponse>,
                response: Response<TvDetailResponse>
            ) {
                if (response.isSuccessful) {
                    resultTv.value = ApiResponse.success(response.body() as TvDetailResponse)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultTv
    }

    companion object {
        const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
}