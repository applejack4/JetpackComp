package com.example.jetpackcomp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomp.Model.entities.RandomObject
import com.example.jetpackcomp.Model.network.RandomDishAPIService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class RandomDishViewModel : ViewModel(){
    private val randomDishService = RandomDishAPIService()

    private val compositeDisposable = CompositeDisposable()

    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomObject.Recipes>()
    val randomDishLoadError = MutableLiveData<Boolean>()

    fun getRandomRecipeFromAPI(){
        loadRandomDish.value = true

        compositeDisposable.add(
            randomDishService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomObject.Recipes>(){
                    override fun onSuccess(t: RandomObject.Recipes) {
                        loadRandomDish.value = true
                        randomDishResponse.value = t
                        randomDishLoadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loadRandomDish.value = false
                        randomDishLoadError.value = true
                        e!!.printStackTrace()
                    }
                }
        ))
    }
}