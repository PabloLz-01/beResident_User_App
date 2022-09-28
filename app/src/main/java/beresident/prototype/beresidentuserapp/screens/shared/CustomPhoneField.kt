package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.layout.*
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

class CustomPhoneField{
    var text: String by mutableStateOf("")
    var number: String by mutableStateOf("")
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomPhoneField(
    values: CustomPhoneField = remember { CustomPhoneField() },
    label: String = "",
    textPadding: Dp? = null,
    bottomPadding: Dp? = null,
) {
    //Properties
    val txtPadding = textPadding ?: DefaultTheme.dimens.grid_0_5
    val btmPadding = bottomPadding ?: 0.dp

    val options = listOf("+52", "+1")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(bottom = btmPadding)) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = txtPadding),
            textAlign = TextAlign.Start,
            color = Grey,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Row{
            /*ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { values.number = selectedOptionText + values.text},
                    modifier = Modifier
                        .width(100.dp)
                        .height(DefaultTheme.dimens.grid_6)
                        .padding(end = DefaultTheme.dimens.grid_0_5),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        unfocusedIndicatorColor = Grey,
                        focusedIndicatorColor = MaterialTheme.colors.secondary
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
            }*/
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                value = values.text,
                onValueChange = {
                    values.text = it
                    values.number = selectedOptionText + values.text
                                },
                singleLine = true,
                textStyle = TextStyle(color = Grey, fontSize = 14.sp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Grey,
                    focusedIndicatorColor = MaterialTheme.colors.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DefaultTheme.dimens.grid_6)
                    .padding(start = DefaultTheme.dimens.grid_1)
            )
        }
    }
}