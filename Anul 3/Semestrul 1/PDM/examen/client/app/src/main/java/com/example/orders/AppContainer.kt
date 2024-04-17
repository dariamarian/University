package com.example.orders


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.example.orders.core.TAG
import com.example.orders.core.data.UserPreferencesRepository
import com.example.orders.core.data.remote.Api
import com.example.orders.todo.data.OrderRepository
import com.example.orders.todo.data.remote.OrderService
import com.example.orders.todo.data.remote.OrderWsClient
import com.example.orders.todo.data.remote.OrderApi

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

class AppContainer(val context: Context) {
    init {
        Log.d(TAG, "init")
    }

    private val orderService: OrderService = Api.retrofit.create(OrderService::class.java)
    private val orderWsClient: OrderWsClient = OrderWsClient(Api.okHttpClient)

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }

    val orderRepository: OrderRepository by lazy {
        OrderRepository(orderService, orderWsClient, database.orderDao())
    }


    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userPreferencesDataStore)
    }

    init {
        OrderApi.orderRepository = orderRepository
    }
}