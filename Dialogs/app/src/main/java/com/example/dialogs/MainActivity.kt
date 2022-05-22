package com.example.dialogs

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialogs.ui.theme.DialogsTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DialogList()
                }
            }
        }
    }
}

@Composable
fun DialogList() {
    Column(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val context = LocalContext.current

        val showAlertDialog = remember { mutableStateOf(false) }
        val showRadioButtonDialog = remember { mutableStateOf(false) }
        val showCheckBoxDialog = remember { mutableStateOf(false) }

        //region simple alert dialog
        OutlinedButton(onClick = {
            showAlertDialog.value = true
        }) {
            Text(text = "Simple Dialog")
        }
        if (showAlertDialog.value) {
            BaseDialog(title = "AlertDialog", state = showAlertDialog) {
                Text(text = "this is demo for simple alert dialog \n click cancel to exit.")
            }
        }
        //endregion

        //region radio button dialog
        OutlinedButton(onClick = {
            showRadioButtonDialog.value = true
        }) {
            Text(text = "Radio Button Dialog ")
        }
        if (showRadioButtonDialog.value) {
            BaseDialog(
                title = "Radio Button Dialog",
                state = showRadioButtonDialog
            ) {
                ShowRadioButtonDialog()
            }
        }
        //endregion

        //region CheckBox dialog
        OutlinedButton(onClick = {
            showCheckBoxDialog.value = true
        }) {
            Text(text = "CheckBox Dialog ")
        }
        if (showCheckBoxDialog.value) {
            BaseDialog(
                title = "CheckBox Dialog",
                state = showCheckBoxDialog
            ) {
                ShowCheckBoxDialog()
            }
        }
        // endregion

        //region date dialog
        val calendar = Calendar.getInstance()
        // Fetching current year, month and day
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = remember { mutableStateOf("$year / ${month + 1} / $day ") }

        val datePicker = DatePickerDialog(
            context, { _, mYear, mMonth, mDayOfMonth ->
                date.value = "$mYear / ${mMonth + 1} / $mDayOfMonth "
            }, year, month, day
        )
        OutlinedTextField(
            value = date.value,
            onValueChange = {
                date.value = it
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        datePicker.show()
                    }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                }
            }
        )
        //endregion

    }
}

@Composable
fun ShowCheckBoxDialog() {
    val checkList = listOf("check 1", "check 2", "check 3", "check 4", "check 5")
    val checkState = checkList.map { remember { mutableStateOf(false) } }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        checkList.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = checkState[index].value,
                        onClick = { checkState[index].value = !checkState[index].value }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checkState[index].value,
                    onCheckedChange = { checkState[index].value = !checkState[index].value })
                Text(
                    text = item,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }


}


@Composable
fun ShowRadioButtonDialog() {
    val optionList = listOf("option 1", "option 2", "option 3", "option 4", "option 5")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(optionList[0]) }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        optionList.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        option == selectedOption,
                        onClick = { onOptionSelected(option) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }


}

@Composable
fun BaseDialog(
    title: String,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {

    val context = LocalContext.current

    AlertDialog(
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = title)
                Divider()
            }
        },
        text = content,
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = {
            state.value = false
        },
        dismissButton = {
            Button(onClick = { state.value = false }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                Toast.makeText(context, "Confirm Dialog", Toast.LENGTH_LONG).show()
                state.value = false
            }) {
                Text(text = "Confirm")
            }
        },
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DialogsTheme {
        DialogList()
    }
}
