package com.dicoding.moviecatalogue.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "releaseData")
    val releaseDate: String?,

    @ColumnInfo(name = "genres")
    val genres: String?,

    @ColumnInfo(name = "runtime")
    val runtime: String?,

    @ColumnInfo(name = "tagline")
    val tagline: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "rating")
    val rating: Double?,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "backdrop")
    val backdrop: String?,
)