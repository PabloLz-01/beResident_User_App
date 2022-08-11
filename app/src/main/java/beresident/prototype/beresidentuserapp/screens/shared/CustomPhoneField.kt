package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey

class CustomPhoneField () {
    var text: String by mutableStateOf("")
    var isFocused: Boolean by mutableStateOf(false)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomPhoneField(
    values: CustomTextField = remember { CustomTextField() },
    label: String = "",
    password: Boolean? = null,
    textPadding: Dp? = null,
    bottomPadding: Dp? = null,
) {
    //Properties
    val textPadding = textPadding ?: DefaultTheme.dimens.grid_0_5
    val bottomPadding = bottomPadding ?: 0.dp

    val options = listOf("+52", "+1")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(bottom = bottomPadding)) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = textPadding),
            textAlign = TextAlign.Start,
            color = Grey,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Row() {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(100.dp),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded,) },
                    colors = TextFieldDefaults.textFieldColors(
                        trailingIconColor = Grey,
                        focusedIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colors.surface,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(color = Grey, fontSize = 12.sp),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        ) {
                            Text(
                                text = selectionOption,
                                color = Grey
                            )
                        }
                    }
                }
            }
            TextField(
                value = values.text,
                onValueChange = {values.text = it},
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                textStyle = TextStyle(color = Grey, fontSize = 12.sp),
                colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        }
    }
}