package com.nurulhidayati_222013.rumahsayur.retrofit


import com.nurulhidayati_222013.rumahsayur.model.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FoodApi {
    @GET("products")
    fun getFood(): Call<FoodResponse>

//    @GET("filter.php?")
//    fun getMealsByCategory(@Query("i") category:String):Call<MealsResponse>
//
//    @GET ("random.php")
//    fun getRandomMeal():Call<RandomMealResponse>
//
//    @GET("lookup.php?")
//    fun getMealById(@Query("i") id:String):Call<RandomMealResponse>
//
//    @GET("search.php?")
//    fun getMealByName(@Query("s") s:String):Call<RandomMealResponse>
}