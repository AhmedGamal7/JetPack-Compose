package com.learning.swipetodelete

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learning.swipetodelete.ui.theme.SwipeToDeleteTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToDeleteTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Greeting() {

    val viewModel: ListViewModel = viewModel()
    val context = LocalContext.current
    val list = viewModel.list
    LazyColumn() {
        items(list.value.size) { index ->
            ListRow(list.value[index]) {
                Toast.makeText(context, "Remove!!", Toast.LENGTH_LONG).show()
                viewModel.removeItemFromList(index)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListRow(listItem: String, removeItem: (Boolean) -> Unit) {

    var visible by remember {
        mutableStateOf(true)
    }
    val iconSize = (-70).dp
    val swipeState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { iconSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(75.dp)
            .swipeable(
                state = swipeState,
                anchors = anchors,
                thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                },
                orientation = Orientation.Horizontal
            )
            .background(Color.Red)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {

            IconButton(
                onClick = {
                    visible = false
                    removeItem(visible)
                },
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item",
                    tint = Color.White
                )
            }
        }

        Box(
            Modifier
                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(listItem, color = Color.Black, fontSize = 24.sp)
        }


    }


}
