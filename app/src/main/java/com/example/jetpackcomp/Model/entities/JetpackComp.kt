package com.example.jetpackcomp.Model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Jetpack_app_comp_table")
data class JetpackComp(
    @ColumnInfo val image : String,
    @ColumnInfo val imageSource : String,
    @ColumnInfo val title : String,
    @ColumnInfo val type : String,
    @ColumnInfo val category : String,
    @ColumnInfo val ingredients : String,

    @ColumnInfo(name = "Cooking_time") val cookingTime: String,
    @ColumnInfo(name = "Instructions") val DirectionsToCook: String,
    @ColumnInfo(name = "Favorite_dish") var FavoriteDish: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id : Int = 0

):Parcelable