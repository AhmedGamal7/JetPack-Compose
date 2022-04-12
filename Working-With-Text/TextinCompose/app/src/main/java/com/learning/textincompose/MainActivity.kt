package com.learning.textincompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learning.textincompose.ui.theme.TextInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SelectableText()
                }
            }
        }
    }
}

@Composable
fun SelectableText() {

    SelectionContainer(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "You can copy me if you press Long click ",
                maxLines = Int.MAX_VALUE,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            DisableSelection {
                Text(
                    text = "You can not copy me",
                    maxLines = Int.MAX_VALUE,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            val annotatedText = buildAnnotatedString {
                pushStyle(SpanStyle(color = Color.DarkGray, fontSize = 18.sp))
                append("This is annotated text by ")
                pop()

                pushStringAnnotation(
                    tag = "SignUp",
                    annotation = "SignUp"
                )
                pushStyle(
                    SpanStyle(
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                append(" SignUp")
                pop()
            }
            val context = LocalContext.current

            ClickableText(text = annotatedText, onClick = { offset ->
                annotatedText.getStringAnnotations(
                    start = offset,
                    end = offset,
                    tag = "SignUp"
                ).firstOrNull()?.let {
                    Toast.makeText(context, "Click", Toast.LENGTH_LONG).show()
                    context.startActivity(Intent(context, SecondActivity::class.java))
                }
            })

            Spacer(modifier = Modifier.height(20.dp))
            var string by remember {
                mutableStateOf("")
            }
            TextField(value = string, onValueChange = { string = it })
        }
    }
}
