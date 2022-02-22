package com.dicoding.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.valueobject.Status
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.dicoding.moviecatalogue.databinding.DetailContentBinding
import com.dicoding.moviecatalogue.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var detailContentBinding: DetailContentBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var favoriteData: FavoriteEntity? = null
    private var isFavorite: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailBinding.detailContent
        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        activityDetailBinding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_ID)
            val type = extras.getString(EXTRA_TYPE)
            if (type != null) {
                when (type) {
                    "movie" -> {
                        viewModel.getMovieDetail(id).observe(this) { movie ->
                            when (movie.status) {
                                Status.LOADING -> activityDetailBinding.progressBar.visibility =
                                    View.VISIBLE
                                Status.SUCCESS -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    activityDetailBinding.collapsingToolbar.title =
                                        movie.data?.title

                                    Glide.with(this)
                                        .load("https://image.tmdb.org/t/p/original/${movie.data?.backdrop}")
                                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                        .into(activityDetailBinding.imgBackdrop)

                                    with(detailContentBinding) {
                                        tvTitle.text = movie.data?.title
                                        tvRelease.text = movie.data?.releaseDate
                                        ratingBar.stepSize = 0.1f
                                        tvRating.text = movie.data?.rating.toString()
                                        tvGenre.text = movie.data?.genres
                                        tvDuration.text =
                                            getString(R.string.duration, movie.data?.runtime)
                                        tvTagline.text =
                                            getString(R.string.tagline, movie.data?.tagline)
                                        tvSeason.visibility = View.GONE
                                        tvEpisode.visibility = View.GONE
                                        tvOverview.text = movie.data?.overview
                                        ratingBar.rating =
                                            (movie.data?.rating?.toFloat() as Float) / 2
                                    }

                                    Glide.with(this)
                                        .load("https://image.tmdb.org/t/p/original/${movie.data?.poster}")
                                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                        .into(detailContentBinding.imgPoster)
                                }
                                Status.ERROR -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }
                    }
                    "tv" -> {
                        viewModel.getTvDetail(id).observe(this) { tv ->
                            when (tv.status) {
                                Status.LOADING -> activityDetailBinding.progressBar.visibility =
                                    View.VISIBLE
                                Status.SUCCESS -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    activityDetailBinding.collapsingToolbar.title = tv.data?.title
                                    Glide.with(this)
                                        .load("https://image.tmdb.org/t/p/original/${tv.data?.backdrop}")
                                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                        .into(activityDetailBinding.imgBackdrop)

                                    with(detailContentBinding) {
                                        tvTitle.text = tv.data?.title
                                        tvRelease.text = tv.data?.releaseDate
                                        ratingBar.stepSize = 0.1f
                                        tvRating.text = tv.data?.rating.toString()
                                        tvGenre.text = tv.data?.genres
                                        tvDuration.text =
                                            getString(R.string.duration, tv.data?.runtime)
                                        tvTagline.text =
                                            getString(R.string.tagline, tv.data?.tagline)
                                        tvSeason.text =
                                            getString(R.string.episodes, tv.data?.episode)
                                        tvEpisode.text =
                                            getString(R.string.seasons, tv.data?.season)
                                        tvOverview.text = tv.data?.overview
                                        ratingBar.rating = (tv.data?.rating?.toFloat() as Float) / 2
                                    }

                                    Glide.with(this)
                                        .load("https://image.tmdb.org/t/p/original/${tv.data?.poster}")
                                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                        .into(detailContentBinding.imgPoster)
                                }
                                Status.ERROR -> {
                                    activityDetailBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val id = intent.extras?.getInt(EXTRA_ID)
        val type = intent.extras?.getString(EXTRA_TYPE)
        if (type == "movie") {
            viewModel.getMovieDetail(id as Int).observe(this) { movie ->
                when (movie.status) {
                    Status.LOADING -> activityDetailBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        activityDetailBinding.progressBar.visibility = View.GONE
                        activityDetailBinding.collapsingToolbar.title = movie.data?.title

                        favoriteData = FavoriteEntity(
                            movie.data?.id,
                            movie.data?.title,
                            movie.data?.rating,
                            movie.data?.overview,
                            movie.data?.poster,
                            "movie"
                        )

                        viewModel.checkFavorite(favoriteData?.id as Int).observe(this) { favorite ->
                            isFavorite = if (favorite) {
                                menu?.findItem(R.id.menu_favorite)
                                    ?.setIcon(R.drawable.ic_favorite_red)
                                true
                            } else {
                                menu?.findItem(R.id.menu_favorite)
                                    ?.setIcon(R.drawable.ic_baseline_favorite_border_24)
                                false
                            }

                        }
                    }
                    Status.ERROR -> {
                        activityDetailBinding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else if (type == "tv") {
            viewModel.getTvDetail(id as Int).observe(this) { tv ->
                when (tv.status) {
                    Status.LOADING -> activityDetailBinding.progressBar.visibility =
                        View.VISIBLE
                    Status.SUCCESS -> {
                        activityDetailBinding.progressBar.visibility = View.GONE
                        activityDetailBinding.collapsingToolbar.title = tv.data?.title

                        favoriteData = FavoriteEntity(
                            tv.data?.id,
                            tv.data?.title,
                            tv.data?.rating,
                            tv.data?.overview,
                            tv.data?.poster,
                            "tv"
                        )

                        viewModel.checkFavorite(favoriteData?.id as Int).observe(this) { favorite ->
                            isFavorite = if (favorite) {
                                menu?.findItem(R.id.menu_favorite)
                                    ?.setIcon(R.drawable.ic_favorite_red)
                                true
                            } else {
                                menu?.findItem(R.id.menu_favorite)
                                    ?.setIcon(R.drawable.ic_baseline_favorite_border_24)
                                false
                            }

                        }
                    }
                    Status.ERROR -> {
                        activityDetailBinding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favorite) {
            if (isFavorite as Boolean) {
                viewModel.deleteFavorite(favoriteData as FavoriteEntity)
            } else {
                viewModel.setFavorite(favoriteData as FavoriteEntity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_TYPE = "type"
    }
}