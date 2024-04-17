package com.example.orders.todo.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.example.orders.todo.data.Order
import com.example.orders.todo.data.OrderRepository

object OrderApi {

    @SuppressLint("StaticFieldLeak")
    lateinit var orderRepository: OrderRepository

}
