package com.learning.swipetodelete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learning.swipetodelete.ui.theme.SwipeToDeleteTheme

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
        itemsIndexed(list.value, key = { _, item ->
            item.hashCode()
        }) { index, item ->
            val dismissState = rememberDismissState(
                confirmStateChange = { dismissValue ->
                    if (dismissValue == DismissValue.DismissedToEnd) {
                        viewModel.removeItemFromList(index)
                    } else if (dismissValue == DismissValue.DismissedToStart) {
                        //share
                        //Toast.makeText(context, "Shared Sting ", Toast.LENGTH_SHORT).show()
                        itemShare(context = context, item = item)
                    }

                    return@rememberDismissState true
                }
            )

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
                directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                dismissThresholds = { direction ->
                    FractionalThreshold(
                        if (direction == DismissDirection.StartToEnd ||
                            direction == DismissDirection.StartToEnd
                        ) 0.25f else 0.5f
                    )
                },
                background = {
                    DismissBackground(dismissState)
                },
                dismissContent = {
                    ListRow(string = item)
                }
            )


        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DismissBackground(dismissState: DismissState) {

    //animate color.
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.Gray
            DismissValue.DismissedToStart -> Color.Green
            DismissValue.DismissedToEnd -> Color.Red
        }
    )
    //icon
    val direction = dismissState.dismissDirection ?: return
    val alignment = when (direction) {
        DismissDirection.EndToStart -> Alignment.CenterEnd
        DismissDirection.StartToEnd -> Alignment.CenterStart
    }

    val icon = when (direction) {
        DismissDirection.EndToStart -> Icons.Default.Share
        DismissDirection.StartToEnd -> Icons.Default.Delete
    }

    //scale
    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) .75f else 1f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(end = 10.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Delete item",
            tint = Color.White,
            modifier = Modifier.scale(scale = scale)
        )
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListRow(string: String) {
    Card(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(string, color = Color.Black, fontSize = 30.sp, textAlign = TextAlign.Center)
    }


}

fun itemShare(context: Context, item: String) {
    //set up intent for share link .
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, item)
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "News App")
    startActivity(
        context,
        Intent.createChooser(shareIntent, null),
        null
    )
}


