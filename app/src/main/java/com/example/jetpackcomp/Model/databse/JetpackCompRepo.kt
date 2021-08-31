package com.example.jetpackcomp.Model.databse

import androidx.annotation.WorkerThread
import com.example.jetpackcomp.Model.entities.JetpackComp
import kotlinx.coroutines.flow.Flow

class JetpackCompRepo(private val jetPackDao : JetpackDao) {

    @WorkerThread
    suspend fun insertJetpackCompData(jetPackComp : JetpackComp){
        jetPackDao.insertFavDishDetail(jetPackComp)
    }

    val allDishesList : Flow<List<JetpackComp>> = jetPackDao.getAllDishesList()

    @WorkerThread
    suspend fun updateFavDishData(jetPackComp: JetpackComp){
        jetPackDao.updateFavDishDetail(jetPackComp)
    }

    val allFavoriteDishesList : Flow<List<JetpackComp>> = jetPackDao.getFavoriteDishes()

    @WorkerThread
    suspend fun deleteFavDishData(jetPackComp: JetpackComp){
        jetPackDao.deleteFavDishDetails(jetPackComp)
    }

    @WorkerThread
    fun filteredDishesList(value : String) : Flow<List<JetpackComp>> = jetPackDao.getFilterDishesList(value)

}

