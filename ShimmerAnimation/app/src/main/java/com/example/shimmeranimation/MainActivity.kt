package com.example.shimmeranimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shimmeranimation.ui.theme.ShimmerAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShimmerAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShimmerAnimation()
                }
            }
        }
    }
}

@Composable
fun ShimmerAnimation() {

    val colors = listOf(
        Color.LightGray.copy(alpha = .8f),
        Color.LightGray.copy(alpha = .2f),
        Color.LightGray.copy(alpha = .8f)
    )

    val transition = rememberInfiniteTransition()
    val animation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = animation.value, y = animation.value)
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        repeat(10) {
            ShimmerLayout(brush)
        }
    }
}

@Composable
fun ShimmerLayout(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column() {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(.7f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(.8f)
                    .background(brush)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShimmerAnimationTheme {
        ShimmerAnimation()
    }
}