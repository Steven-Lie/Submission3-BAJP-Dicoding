package com.dicoding.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.databinding.ItemListBinding

class ListAdapter : PagedListAdapter<CatalogueEntity, ListAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val catalogue = getItem(position)
        with(holder) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original/${catalogue?.poster}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .into(binding.imgPoster)

            with(binding) {
                tvTitle.text = catalogue?.title
                tvOverview.text = catalogue?.overview
                ratingBar.stepSize = 0.1f
                tvRating.text = catalogue?.rating.toString()
                ratingBar.rating = (catalogue?.rating?.toFloat() as Float) / 2
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(catalogue as CatalogueEntity)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(catalogue: CatalogueEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CatalogueEntity>() {
            override fun areItemsTheSame(
                oldItem: CatalogueEntity,
                newItem: CatalogueEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CatalogueEntity,
                newItem: CatalogueEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}