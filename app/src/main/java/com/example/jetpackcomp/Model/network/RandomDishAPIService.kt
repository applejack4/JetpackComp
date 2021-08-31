package com.example.jetpackcomp.Model.network

import com.example.jetpackcomp.Model.entities.RandomObject
import com.example.jetpackcomp.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomDishAPIService {
    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishAPI::class.java)

    fun getRandomDish() : Single<RandomObject.Recipes>{
        return api.getDishes(Constants.API_KEY_VALUE,Constants.TAGS_VALUE,
            Constants.LIMIT_LICENSE_VALUE,
         Constants.NUMBER_VALUE)
    }
}