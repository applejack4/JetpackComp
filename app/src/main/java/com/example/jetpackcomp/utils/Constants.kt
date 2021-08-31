package com.example.jetpackcomp.utils

object Constants {
    const val Dish_type : String = "DishType"
    const val Dish_Category : String = "DishCategory"
    const val Dish_Cooking_Time : String = "DishCookingTime"

    const val LOCAL_IMAGE_SOURCE : String = "LOCAL"
    const val ONLINE_IMAGE_SOURCE : String = "ONLINE"
    const val EXTRA_DISH_DETAILS : String = "DishDetails"

    const val ALL_ITEMS : String = "All"
    const val FILTER_SELECTION : String = "FilterSelection"

    const val API_ENDPOINT : String = "recipes/random"

    const val API_KEY : String = "apiKey"
    const val TAGS : String = "tags"
    const val LIMIT_LICENSE : String = "limitLicense"
    const val NUMBER : String = "number"

    const val BASE_URL = "https://api.spoonacular.com/"

    const val API_KEY_VALUE : String = "c0fd44b72db84f27bf5ad4ef6ee60f79"
    const val LIMIT_LICENSE_VALUE : Boolean = true
    const val TAGS_VALUE : String = "vegetarian,dessert"
    const val NUMBER_VALUE : Int = 1


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