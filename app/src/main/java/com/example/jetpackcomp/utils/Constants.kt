package com.example.jetpackcomp.utils

import javax.sql.StatementEvent

object Constants {
    const val Dish_type : String = "DishType"
    const val Dish_Category : String = "DishCategory"
    const val Dish_Cooking_Time : String = "DishCookingTime"

    const val LOCAL_IMAGE_SOURCE : String = "LOCAL"
    const val ONLINE_IMAGE_SOURCE : String = "ONLINE"

    fun dishTypes():ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Breakfast")
        list.add("Lunch")
        list.add("Snacks")
        list.add("Dinner")
        list.add("Salad")
        list.add("side dish")
        list.add("Desert")
        list.add("Other")
        return list
    }

    fun dishCategory():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Pizza")
        list.add("BBQ")
        list.add("Beer")
        list.add("Bread")
        list.add("Burger")
        list.add("Sandwich")
        list.add("Wraps")
        list.add("Chicken")
        list.add("Others")
        return list
    }
}