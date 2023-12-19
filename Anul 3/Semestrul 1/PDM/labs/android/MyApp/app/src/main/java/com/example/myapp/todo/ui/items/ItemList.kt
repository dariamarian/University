package com.example.myapp.todo.ui.items

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.todo.data.Item

typealias OnItemFn = (id: String?) -> Unit

@Composable
fun ItemList(itemList: List<Item>, onItemClick: OnItemFn, modifier: Modifier) {
    Log.d("ItemList", "recompose")
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFE1BEE7)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            items(itemList) { item ->
                ItemDetail(item, onItemClick)
            }
        }
    }
}

@Composable
fun ItemDetail(item: Item, onItemClick: OnItemFn) {
    val annotatedString = buildAnnotatedString {
        with(item) {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Title:")
            pop()
            append(" $title\n")

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Genre:")
            pop()
            append(" $genre\n")

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Release Date:")
            pop()
            append(" $releaseDate\n")

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Rating:")
            pop()
            append(" $rating\n")

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Watched:")
            pop()
            append(" ${if (watched) "Yes" else "No"}")

            append("\n")
        }
    }

    Row(){
        ClickableText(
            text = annotatedString,
            style = TextStyle(
                fontSize = 22.sp,
                color = Color.Black // Set text color here
            ),
            onClick = { onItemClick(item._id) }
        )
    }
}
