package com.example.orders.todo.data

import android.content.Context
import android.util.Log
import com.example.orders.core.TAG
import com.example.orders.core.showSimpleNotificationWithTapAction
import com.example.orders.todo.data.local.OrderDao
import com.example.orders.todo.data.remote.OrderEvent
import com.example.orders.todo.data.remote.OrderService
import com.example.orders.todo.data.remote.OrderWsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


class OrderRepository(
    private val orderService: OrderService,
    private val orderWsClient: OrderWsClient,
    private val orderDao: OrderDao
) {
    val orderStream by lazy {
        Log.d(TAG, "Perform a getAll query")
        val flow = orderDao.getAll()
        Log.d(TAG, "Get all orders from the database SUCCEEDED")
        flow
    }

    private lateinit var context: Context

    init {
        Log.d(TAG, "init")
    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getOrderEvents().collect {
                Log.d(TAG, "Order event collected $it")
                if (it.isSuccess) {
                    val orderEvent = it.getOrNull();
                    when (orderEvent?.type) {
//                        "created" -> handleOrderCreated(orderEvent.payload)
//                        "updated" -> handleOrderUpdated(orderEvent.payload)
//                        "deleted" -> handleOrderDeleted(orderEvent.payload)
                        null -> {
                            for (order in orderEvent?.payload!!) {
                                Log.d(TAG, order.toString())
                                handleOrderCreated(order)
                            }
                        }
                    }
                    Log.d(TAG, "Order event handled $orderEvent")
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            orderWsClient.closeSocket()
        }
    }

    private suspend fun getOrderEvents(): Flow<Result<OrderEvent>> = callbackFlow {
        Log.d(TAG, "getOrderEvents started")
        orderWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    trySend(kotlin.Result.success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() });
        awaitClose { orderWsClient.closeSocket() }
    }


    private suspend fun handleOrderUpdated(order: Order) {
        Log.d(TAG, "handleOrderUpdated... $order")
        val updated = orderDao.update(order)
        Log.d("handleOrderUpdated exited with code = ", updated.toString())
    }

    private suspend fun handleOrderCreated(order: Order) {
        Log.d(TAG, "handleOrderCreated... for order $order")
        if (order.code >= 0) {
            Log.d(TAG, "handleOrderCreated - insert/update an existing order")
            orderDao.insert(order)
            Log.d(TAG, "handleOrderCreated - insert/update an existing order SUCCEEDED")
        } else {
            val randomNumber = (-10000000..-1).random()
            val localOrder = order.copy(code = randomNumber)
            Log.d(TAG, "handleOrderCreated - create a new order locally $localOrder")
            orderDao.insert(localOrder)
            Log.d(TAG, "handleOrderCreated - create a new order locally SUCCEEDED")
        }
    }

    fun setToken(token: String) {
        orderWsClient.authorize(token)
    }

    fun setContext(context: Context) {
        this.context = context
    }

    suspend fun updateOrderWithQuantity(order: Order, onResult: (Boolean) -> Unit) {
        Log.d(TAG, "updateOrderWithQuantity $order...")

        val item = Item(code = order.code, quantity = order.quantity ?: 0)
        Log.d(TAG, "updateOrderWithQuantity on SERVER for item -> $item")

        try {
            val response = orderService.postItem(item)

            if (response.isSuccessful) {
                Log.d(TAG, "response $response")
                Log.d(TAG, "updateOrderWithQuantity on SERVER -> $order SUCCEEDED")
                handleOrderUpdated(order)
                onResult(true)
            } else {
                Log.d(TAG, "updateOrderWithQuantity on SERVER -> $order FAILED with status ${response.code()}")
                onResult(false)
            }
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "updateOrderWithQuantity on SERVER -> $order FAILED $e")
            showSimpleNotificationWithTapAction(
                context,
                "Orders Channel",
                1,
                "Server Error",
                "Could not connect to the server. Please check your network connection and try again."
            )
            onResult(false)

        } catch (e: Exception) {
            Log.d(TAG, "updateOrderWithQuantity on SERVER -> $order FAILED $e")
            showSimpleNotificationWithTapAction(
                context,
                "Orders Channel",
                1,
                "Server Error",
                "Could not connect to the server. Please check your network connection and try again."
            )
            onResult(false)
        }
    }

    suspend fun updateOrderWithQuantityLocally(order: Order) {
        handleOrderUpdated(order)
    }
}