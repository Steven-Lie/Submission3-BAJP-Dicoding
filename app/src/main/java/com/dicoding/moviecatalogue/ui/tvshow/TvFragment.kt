package com.dicoding.moviecatalogue.ui.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.valueobject.Resource
import com.dicoding.moviecatalogue.valueobject.Status
import com.dicoding.moviecatalogue.adapter.FavoriteAdapter
import com.dicoding.moviecatalogue.adapter.ListAdapter
import com.dicoding.moviecatalogue.data.local.entity.CatalogueEntity
import com.dicoding.moviecatalogue.data.local.entity.FavoriteEntity
import com.dicoding.moviecatalogue.databinding.FragmentTvBinding
import com.dicoding.moviecatalogue.ui.detail.DetailActivity
import com.dicoding.moviecatalogue.utils.SortUtils
import com.dicoding.moviecatalogue.viewmodel.ViewModelFactory

class TvFragment(private val from: String) : Fragment() {
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: TvViewModel

    private val homeObserver = Observer<Resource<PagedList<CatalogueEntity>>> { catalogue ->
        val listAdapter = ListAdapter()
        when (catalogue.status) {
            Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
            Status.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                listAdapter.submitList(catalogue.data)
            }
            Status.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

        with(binding?.rvTv) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = listAdapter
        }

        listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(catalogue: CatalogueEntity) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, catalogue.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE)
                startActivity(intent)
            }
        })
    }

    private val favoriteObserver = Observer<PagedList<FavoriteEntity>> { favorite ->
        val favoriteAdapter = FavoriteAdapter()
        binding?.progressBar?.visibility = View.GONE
        favoriteAdapter.submitList(favorite)
        if (favorite.isEmpty()) {
            binding?.tvEmpty?.visibility = View.VISIBLE
        }

        with(binding?.rvTv) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = favoriteAdapter
        }

        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: FavoriteEntity) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, favorite.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE)
                startActivity(intent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(
                this,
                factory
            )[TvViewModel::class.java]

            if (from == "home") {
                binding?.tvEmpty?.visibility = View.GONE
                viewModel.getTvs(SortUtils.POPULAR).observe(viewLifecycleOwner, homeObserver)
            } else if (from == "favorite") {
                binding?.progressBar?.visibility = View.VISIBLE
                viewModel.getFavoriteTvs(SortUtils.POPULAR)
                    .observe(viewLifecycleOwner, favoriteObserver)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_popular -> sort = SortUtils.POPULAR
            R.id.action_az -> sort = SortUtils.AZ
            R.id.action_za -> sort = SortUtils.ZA
        }

        if (from == "home") {
            binding?.tvEmpty?.visibility = View.GONE
            viewModel.getTvs(sort).observe(viewLifecycleOwner, homeObserver)
        } else if (from == "favorite") {
            binding?.progressBar?.visibility = View.VISIBLE
            viewModel.getFavoriteTvs(sort).observe(viewLifecycleOwner, favoriteObserver)
        }

        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TYPE = "tv"
    }
}