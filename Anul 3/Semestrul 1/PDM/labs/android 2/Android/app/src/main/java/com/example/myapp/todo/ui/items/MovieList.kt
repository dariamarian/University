package com.example.myapp.todo.ui.items

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.todo.data.Movie

typealias OnItemFn = (id: String?) -> Unit

@Composable
fun ItemList(movieList: List<Movie>, onItemClick: OnItemFn, modifier: Modifier) {
    Log.d("ItemList", "recompose")
    LazyColumn(
        modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
    ) {
        items(movieList) { item ->
            ItemDetail(item, onItemClick)
        }
    }
}

@Composable
fun ItemDetail(movie: Movie, onItemClick: OnItemFn) {
//    Log.d("ItemDetail", "recompose id = ${item.id}")
    Row {
        Column {
            ClickableText(text = AnnotatedString(movie.name),
                    style = TextStyle(
                            fontSize = 24.sp,
                    ), onClick = { onItemClick(movie._id) })
        }
    }
}
