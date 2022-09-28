package beresident.prototype.beresidentuserapp.screens.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey

class CustomTxtField {
    var text: String by mutableStateOf("")
}

@Composable
fun CustomTxtField(
    values: CustomTxtField = remember { CustomTxtField() },
    label: String = "",
    userPassword: Boolean? = null,
    btmPadding: Dp? = null,
) {
    //Properties
    val password = userPassword ?: false
    val bottomPadding = btmPadding ?: 0.dp
    var hidden by remember { mutableStateOf(password)}

    Column(modifier = Modifier.padding(bottom = bottomPadding)) {
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = Grey,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = values.text,
            onValueChange = {values.text = it},
            singleLine = true,
            textStyle = TextStyle(color = Grey, fontSize = 14.sp),
            keyboardOptions =
                if (password) KeyboardOptions(keyboardType = KeyboardType.Password)
                else KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation =
                if (hidden) PasswordVisualTransformation()
                else VisualTransformation.None,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Grey,
                focusedIndicatorColor = MaterialTheme.colors.secondary
            ),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (password) {
                    IconButton(
                        modifier = Modifier.width(24.dp),
                        onClick = {hidden = !hidden}
                    ) {
                        val vector = painterResource(
                            if (hidden) R.drawable.ic_visibility_fill0_wght400_grad0_opsz48
                            else R.drawable.ic_visibility_off_fill0_wght400_grad0_opsz48
                        )
                        Icon (
                            painter = vector,
                            contentDescription = null,
                            tint = Grey
                        )
                    }
                }
            }
        )
    }
}