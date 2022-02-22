package com.dicoding.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object SortUtils {
    const val POPULAR = "popular"
    const val AZ = "az"
    const val ZA = "za"

    fun getSortedQuery(filter: String, type: String): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("select * from catalogueEntity where type = '$type'")
        when (filter) {
            POPULAR -> {
                simpleQuery.append(" ORDER BY rating DESC")
            }
            AZ -> {
                simpleQuery.append(" ORDER BY title ASC")
            }
            ZA -> {
                simpleQuery.append(" ORDER BY title DESC")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedFavoriteQuery(filter: String, type: String): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("select * from favoriteEntity where type = '$type'")
        when (filter) {
            POPULAR -> {
                simpleQuery.append("ORDER BY rating DESC")
            }
            AZ -> {
                simpleQuery.append("ORDER BY title ASC")
            }
            ZA -> {
                simpleQuery.append("ORDER BY title DESC")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}