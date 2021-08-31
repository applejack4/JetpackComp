package com.example.jetpackcomp.ViewModel

import androidx.lifecycle.*
import com.example.jetpackcomp.Model.databse.JetpackCompRepo
import com.example.jetpackcomp.Model.entities.JetpackComp
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class JetPackCompViewModel(private val repository : JetpackCompRepo) : ViewModel(){

    fun insert(dish : JetpackComp) = viewModelScope.launch{
        repository.insertJetpackCompData(dish)
    }

    val allDishesList : LiveData<List<JetpackComp>> = repository.allDishesList.asLiveData()

    fun update(dish: JetpackComp) = viewModelScope.launch {
        repository.updateFavDishData(dish)
    }

    val favoriteDishes : LiveData<List<JetpackComp>> = repository.allFavoriteDishesList.asLiveData()

    fun delete(dish: JetpackComp) = viewModelScope.launch {
        repository.deleteFavDishData(dish)
    }

    fun getFilteredList(dish: String) : LiveData<List<JetpackComp>> = repository.filteredDishesList(dish).asLiveData()
}

class JetPackCompViewModelFactory(private val repository: JetpackCompRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JetPackCompViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return JetPackCompViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}