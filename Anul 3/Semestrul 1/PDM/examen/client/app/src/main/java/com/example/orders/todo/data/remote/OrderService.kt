package com.example.orders.todo.data.remote

import com.example.orders.todo.data.Item
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.orders.todo.data.Order
import com.google.gson.annotations.SerializedName
import retrofit2.Response

interface OrderService {

    @Headers("Content-Type: application/json")
    @POST("/item")
    suspend fun postItem(
        @Body item: Item
    ): Response<ResponseModel>

}

data class ResponseModel(@SerializedName("text") val textValue: String)
