package com.learning.multiselectlistitem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.learning.multiselectlistitem.ui.theme.MultiSelectListItemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiSelectListItemTheme {

                Surface(color = MaterialTheme.colors.background) {
                    val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
                    ShowList(viewModel)
                }

            }
        }
    }
}

@Composable
fun ShowList(viewModel: MainViewModel) {
    val myList = viewModel.myList.collectAsState()
    val selectedList = viewModel.selectedItem.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(myList.value) { index, item ->
            ListRow(
                item = item,
                selected = selectedList.value.contains(index)
            ) {
                viewModel.listItemLongClick(index)
            }
        }
    }
}

@Composable
fun ListRow(item: String, selected: Boolean, longClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(onClick = longClick)
//            .pointerInput(Unit) {
//                detectTapGestures(onLongPress = longClick)
//            }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = Color.Green,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}
