package com.learning.bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.bottomsheet.ui.theme.BottomSheetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetTheme {
                ShowBottomSheet()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowBottomSheet() {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Color.DarkGray,
        sheetElevation = 12.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 30.dp),
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "Hello from sheet")
            }

        }) {
        MainContent {
            scope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                    bottomSheetScaffoldState.bottomSheetState.expand()
                else
                    bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
}

@Composable
fun MainContent(changeSheetState: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {
            //scope to open/close bottom sheet
            changeSheetState()
        }) {
            Text(text = "Toggle Bottom Sheet")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BottomSheetTheme {
        ShowBottomSheet()
    }
}