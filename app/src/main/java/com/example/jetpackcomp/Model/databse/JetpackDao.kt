package com.example.jetpackcomp.Model.databse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jetpackcomp.Model.entities.JetpackComp
import kotlinx.coroutines.flow.Flow


@Dao
interface JetpackDao {
    @Insert
    suspend fun insertFavDishDetail(jetPackComp : JetpackComp)

    @Query("SELECT * FROM  JETPACK_APP_COMP_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<JetpackComp>>
}

