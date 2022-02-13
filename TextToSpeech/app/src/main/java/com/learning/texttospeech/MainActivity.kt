package com.learning.texttospeech

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learning.texttospeech.ui.theme.TextToSpeechTheme
import com.learning.texttospeech.ui.theme.textFieldColor
import java.util.*


class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextToSpeechTheme {
                textToSpeech = TextToSpeech(this, this)
                Surface(color = MaterialTheme.colors.background) {
                    MainCompose(textToSpeech!!)
                }
            }
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(
                    this,
                    "The Language specified is not supported!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //textToSpeech Ready.
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()
    }

}

@Composable
fun MainCompose(textToSpeech: TextToSpeech) {

    val context = LocalContext.current

    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(ScrollState(0))
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        val t = text.text
                        text = text.copy(
                            selection = TextRange(0, t.length)
                        )
                    }
                },
            value = text,
            maxLines = Int.MAX_VALUE,
            onValueChange = { text = it },
            placeholder = {
                Text(text = "Enter your Text")
            },
            label = {
                Text(text = "TextToSpeech")
            },
            textStyle = TextStyle(
                color = textFieldColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.SansSerif,
            )
        )

        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = {
                speakText(context = context, textToSpeech, text = text.text)
            }
        ) {
            Text(text = "Speak", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        }

    }

}

fun speakText(context: Context, textToSpeech: TextToSpeech, text: String) {

    if (text.isNotEmpty()) {
        textToSpeech.setSpeechRate(.5f)
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "t1")

    } else {
        textToSpeech.speak("Text cannot be empty", TextToSpeech.QUEUE_FLUSH, null, "t1")
        Toast.makeText(context, "Text cannot be empty", Toast.LENGTH_LONG).show()
    }
}
