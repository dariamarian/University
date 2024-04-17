package com.example.myapp.todo.ui.items

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
    // Animation state
    val alpha = remember { Animatable(0f) } // Start fully transparent
    val translateY = remember { Animatable(-100f) } // Start slightly above
    val rotation = remember { Animatable(0f) } // Start with no rotation

    // Start the animations when the composable enters the composition
    LaunchedEffect(movie) {
        alpha.animateTo(
            targetValue = 1f, // Fade in to fully opaque
            animationSpec = tween(durationMillis = 500)
        )
        translateY.animateTo(
            targetValue = 0f, // Translate to original position
            animationSpec = tween(durationMillis = 500)
        )
        rotation.animateTo(
            targetValue = 360f, // Rotate 360 degrees
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Row(
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha.value
            this.translationY = translateY.value
            this.rotationZ = rotation.value
        }
    ) {
        Column {
            ClickableText(
                text = AnnotatedString(movie.name),
                style = TextStyle(fontSize = 24.sp),
                onClick = { onItemClick(movie._id) }
            )
        }
    }
}
