package com.example.jetpackcomp.Model.databse

import androidx.room.*
import com.example.jetpackcomp.Model.entities.JetpackComp
import kotlinx.coroutines.flow.Flow


@Dao
interface JetpackDao {
    @Insert
    suspend fun insertFavDishDetail(jetPackComp : JetpackComp)

    @Update
    suspend fun updateFavDishDetail(jetPackComp: JetpackComp)

    @Delete
    suspend fun deleteFavDishDetails(jetPackComp: JetpackComp)

    @Query("SELECT * FROM  JETPACK_APP_COMP_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<JetpackComp>>

    @Query("SELECT * FROM JETPACK_APP_COMP_TABLE where favorite_dish = 1")
    fun getFavoriteDishes() : Flow<List<JetpackComp>>

    @Query("SELECT * FROM JETPACK_APP_COMP_TABLE WHERE type = :filterType")
    fun getFilterDishesList(filterType : String) : Flow<List<JetpackComp>>

}

