package com.learning.heterogeneouslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learning.heterogeneouslist.ui.theme.HeterogeneousListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeterogeneousListTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ShowList()
                }
            }
        }
    }
}

@Composable
fun ShowList() {
    val mainList = mutableListOf<String>()
    val subList = mutableListOf<String>()
    for (i in 1 until 100) {
        mainList.add("Item $i")
    }
    for (i in 1 until 10) {
        subList.add("Item $i")
    }


    LazyColumn {
        itemsIndexed(mainList) { index, item ->
            if (index % 7 == 0) {
                ShowHorizontalList(subList)
            } else {
                ShowVerticalList(item)
            }
        }
    }

}

@Composable
fun ShowHorizontalList(list: MutableList<String>) {
    LazyRow(modifier = Modifier.fillMaxSize()) {
        items(list) { item ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .height(75.dp)
                    .width(75.dp),
                shape = RoundedCornerShape(15.dp),
                backgroundColor = Color.DarkGray
            ) {
                Text(
                    text = item,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun ShowVerticalList(item: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.DarkGray
    ) {
        Text(
            text = item,
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
    }
}
