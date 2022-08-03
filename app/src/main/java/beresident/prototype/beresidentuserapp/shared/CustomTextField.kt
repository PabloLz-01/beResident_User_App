package beresident.prototype.beresidentuserapp.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import beresident.prototype.beresidentuserapp.ui.theme.LightGrey

class CustomTextField () {
    var text: String by mutableStateOf("")
}

@Composable
fun CustomTextField(
    values: CustomTextField = remember { CustomTextField() },
    label: String = "",
    password: Boolean? = null,
    textPadding: Dp? = null,
    bottomPadding: Dp? = null,
) {
    //Properties
    val password = password ?: false
    val textPadding = textPadding ?: DefaultTheme.dimens.grid_0_5
    val bottomPadding = bottomPadding ?: 0.dp

    var seePassword = password
    var hidden by remember { mutableStateOf(seePassword)}

    Column(modifier = Modifier.padding(bottom = bottomPadding)) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = textPadding),
            textAlign = TextAlign.Start,
            color = Grey,
            fontSize = 12.sp
        )
        BasicTextField(
            value = values.text,
            onValueChange = {values.text = it},
            singleLine = true,
            textStyle = TextStyle(color = Grey, fontSize = 12.sp, lineHeight = 10.sp),
            keyboardOptions =
            if (password) KeyboardOptions(keyboardType = KeyboardType.Password)
            else KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation =
            if (hidden) PasswordVisualTransformation()
            else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(LightGrey, RoundedCornerShape(8.dp))
                        .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
                        .height(DefaultTheme.dimens.grid_6)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)){
                        innerTextField()
                    }
                    if (password) {
                        Box(modifier = Modifier.padding(all = DefaultTheme.dimens.grid_0_5).height(24.dp)){
                            IconButton(onClick = {hidden = !hidden}) {
                                val vector = painterResource(
                                    if (hidden) R.drawable.ic_visibility_fill0_wght400_grad0_opsz48
                                    else R.drawable.ic_visibility_off_fill0_wght400_grad0_opsz48
                                )
                                val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                                Icon (painter = vector, contentDescription = description, tint = Grey)
                            }
                        }
                    } else null
                }
            }
        )
    }
}