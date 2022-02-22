package com.dicoding.moviecatalogue.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalogueEntity")
data class CatalogueEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "rating")
    val rating: Double?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "type")
    val type: String?,
)