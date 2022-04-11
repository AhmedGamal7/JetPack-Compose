package com.learning.swipeaction.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.swipeaction.R
import com.learning.swipeaction.data.model.Student
import com.learning.swipeaction.ui.theme.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeActionTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ShowList()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowList(viewModel: MainActivityViewModel = MainActivityViewModel()) {

    val darkMode = isSystemInDarkTheme()
    val list = viewModel.studentList
    Column {
        LazyColumn(
            modifier = Modifier.weight(.9f)
        ) {

            items(list.value, key = {
                it.hashCode()
            }) { student ->

                val shareAction = SwipeAction(
                    onSwipe = {
                        //share action
                        Log.i("My Tag", "Share")
                    },
                    icon = painterResource(id = R.drawable.ic_share),
                    background = Color.Green,
                    isUndo = false
                )
                val deleteAction = SwipeAction(
                    onSwipe = {
                        //share action
                        Log.i("My Tag", "Delete")
                        viewModel.deleteItem(student = student)
                    },
                    icon = painterResource(id = R.drawable.ic_delete),
                    background = Color.Red,
                    isUndo = true
                )
                SwipeableActionsBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        ),
                    startActions = listOf(shareAction),
                    endActions = listOf(deleteAction),
                    swipeThreshold = 50.dp
                ) {
                    ListRow(student)
                }
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .fillMaxWidth()
                )
            }
        }

        Button(
            onClick = {
                list.value = list.value.shuffled()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = if (darkMode) ButtonDefaults.buttonColors(backgroundColor = ShuffleButtonColorLightMode)
            else ButtonDefaults.buttonColors(backgroundColor = ShuffleButtonColorNightMode)
        ) {
            Text(
                text = "Shuffle",
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.padding(10.dp)
            )
        }
    }


}

@Composable
fun ListRow(student: Student) {
    val darkMode = isSystemInDarkTheme()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(color = if (darkMode) StatusBarColorNight else StatusBarColorLight)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .size(50.dp)
                .padding(2.dp)
                .background(Purple200)
        )
        Spacer(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
        )
        Column(modifier = Modifier.weight(.7f)) {
            Text(text = student.name, fontSize = MaterialTheme.typography.h5.fontSize)
            Text(text = student.age.toString(), fontSize = MaterialTheme.typography.h6.fontSize)
        }
    }
}
