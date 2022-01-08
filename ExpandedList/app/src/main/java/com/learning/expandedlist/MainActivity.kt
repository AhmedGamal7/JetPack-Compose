package com.learning.expandedlist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learning.expandedlist.data.ParentModel
import com.learning.expandedlist.ui.theme.ExpandedListTheme
import com.learning.expandedlist.ui.theme.Purple500

const val duration = 300

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpandedListTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShowList()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ShowList() {

    val viewModel: MainViewModel = viewModel()
    val parentList = viewModel.parentList.collectAsState()
    val expandedCard = viewModel.expandedCardList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(parentList.value) { index, item ->
            ListRow(
                item = item,
                expanded = expandedCard.value.contains(index)
            ) { viewModel.cardArrowClick(index) }
        }
    }
}

@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ListRow(item: ParentModel, expanded: Boolean, cardArrowClick: () -> Unit) {

    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 1000)
    }, label = "rotationDegreeTransition") {
        if (expanded) 180f else 0f
    }

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = Color.White,
        elevation = 10.dp,
        shape = RoundedCornerShape(15)
    ) {
        Column(
            modifier = Modifier.width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.align(alignment = Alignment.TopEnd),
                ) {
                    IconButton(
                        onClick = cardArrowClick,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_downward_24),
                                contentDescription = "",
                                modifier = Modifier.rotate(arrowRotationDegree),
                                tint = Color.Blue
                            )
                        }
                    )
                }
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Text(
                        text = item.name,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(alignment = Alignment.Center),
                        color = Color.Blue,
                        fontWeight = FontWeight.W600
                    )
                }
            }
            ExpandedView(expanded = expanded, item.children)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandedView(expanded: Boolean, children: List<String>) {

    val enterExpand = remember {
        expandVertically(animationSpec = tween(duration))
    }

    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = duration,
                easing = FastOutLinearInEasing
            )
        )
    }

    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = duration,
                easing = LinearOutSlowInEasing
            )
        )
    }

    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(duration))
    }

    AnimatedVisibility(
        visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            for (i in children) {
                Text(
                    text = i,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Purple500
                )
            }

        }
    }
}


