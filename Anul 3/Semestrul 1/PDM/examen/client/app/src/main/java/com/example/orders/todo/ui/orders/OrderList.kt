package com.example.orders.todo.ui.orders


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orders.todo.data.Order
import com.example.orders.todo.ui.order.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class FilterOption {
    SHOW_ALL,
    SHOW_WITH_QUANTITIES
}

@Composable
fun OrderList(
    orderList: List<Order>,
    modifier: Modifier,
    ordersViewModel: OrdersViewModel
) {
    Log.d("OrderList", "recompose")

    var filterOption by remember { mutableStateOf(FilterOption.SHOW_ALL) }

    val filteredOrders = when (filterOption) {
        FilterOption.SHOW_ALL -> orderList
        FilterOption.SHOW_WITH_QUANTITIES -> orderList.filter { it.quantity != null }
    }

    var failedOrders by remember { mutableStateOf<List<Order>>(emptyList()) }
    var isError by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(4.dp)
                    .align(Alignment.TopCenter)
            )
        }

        Column {

            Row(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                RadioButton(
                    selected = filterOption == FilterOption.SHOW_ALL,
                    onClick = { filterOption = FilterOption.SHOW_ALL },
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text("No filter")

                RadioButton(
                    selected = filterOption == FilterOption.SHOW_WITH_QUANTITIES,
                    onClick = { filterOption = FilterOption.SHOW_WITH_QUANTITIES },
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                )
                Text("Filter")
            }

            LazyColumn(
                modifier = Modifier
                    .weight(0.9f)
            ) {
                items(filteredOrders) {
                    val failed = failedOrders.find { f -> f == it }
                    val isFailed = failed != null
                    OrderDetail(id = it.code, order = it, isHighlighted = isFailed)
                }
            }

            val coroutineScope = rememberCoroutineScope()
            Row {

                Button(
                    onClick = {
                        failedOrders = emptyList()

                        coroutineScope.launch {
                            isLoading = true
                            for (order in orderList) {
                                if (order.quantity != null) {
                                    val result =
                                        ordersViewModel.updateOrderWithQuantity(order) { isSuccess ->
                                            if (!isSuccess) {
                                                failedOrders = failedOrders + order
                                            }
                                        }
                                    if (result != "Success") {
                                        isError = true
                                        text = result
                                        failedOrders = failedOrders + order
                                    }
                                }
                            }
                            isLoading = false
                        }
                    },
                    modifier = Modifier.align(Alignment.Bottom)
                ) {
                    Text(text = "Submit")
                }
            }

        }

        LaunchedEffect(isError) {
            if (!isError) {
                delay(3500L)
                text = ""
            } else {
                delay(5000L)
                isError = false
            }
        }
    }
}

@Composable
fun OrderDetail(id: Int, order: Order, isHighlighted: Boolean = false) {
    Log.d("OrderDetail", "recompose for $order")
    val orderViewModel = viewModel<OrderViewModel>(factory = OrderViewModel.Factory(id))
    val gradientColors = listOf(
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    )
    val textColor = MaterialTheme.colorScheme.onPrimary
    var isEditingQuantity by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    //ErrorInOrder(error = isError, text = text)

    LaunchedEffect(isError) {
        if (!isError) {
            delay(3500L)
            text = ""
        } else {
            delay(5000L)
            isError = false
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(Color(0xFFC71585))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var quantity by remember { mutableStateOf(order.quantity.toString() ?: "0") }
        Text(
            text = "${order.name}",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (isHighlighted) Color.Red else textColor
            ),
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = quantity.toString(),
            onValueChange = {
                // Update the quantity in the view model for items
                try {
                    val q = it.toInt()
                    val newOrder = order.copy(quantity = q)
                    orderViewModel.updateOrderWithQuantity(newOrder)
                } catch (e: Exception) {
                    Log.d("OrderDetail", "Error updating x quantity: ${e.message}")
                    isError = true
                    text = e.message ?: ""
                }
                quantity = it
                isEditingQuantity = false
            },
            readOnly = isEditingQuantity,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = if (isHighlighted) Color.Red else textColor // Highlight based on the parameter,
            ),
            modifier = Modifier.width(90.dp),
        )
    }
}
