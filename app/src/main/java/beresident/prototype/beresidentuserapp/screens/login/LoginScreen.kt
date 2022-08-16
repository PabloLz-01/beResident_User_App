package beresident.prototype.beresidentuserapp.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.R
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.model.UserModel
import beresident.prototype.beresidentuserapp.screens.shared.CheckBox
import beresident.prototype.beresidentuserapp.screens.shared.CustomButton
import beresident.prototype.beresidentuserapp.screens.shared.CustomCheckbox
import beresident.prototype.beresidentuserapp.screens.shared.CustomTextField
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import beresident.prototype.beresidentuserapp.ui.theme.Grey
import beresident.prototype.beresidentuserapp.ui.theme.snackbarError
import beresident.prototype.beresidentuserapp.screens.login.widgets.AppHeader
import beresident.prototype.beresidentuserapp.screens.login.widgets.Division
import beresident.prototype.beresidentuserapp.screens.login.widgets.LoginHeader
import beresident.prototype.beresidentuserapp.usecases.ApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(navController: NavController){
    val emailState = remember { CustomTextField() }
    val passwordState = remember { CustomTextField() }
    var checkbox =  remember { CustomCheckbox() }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    var showSnackbar = true

    var snackbarText: String
    var snackbarColor: Color = snackbarError

    var context = LocalContext.current
    val response = remember { mutableStateOf("") }

    Scaffold (
        backgroundColor = MaterialTheme.colors.primaryVariant,
        scaffoldState = scaffoldState,
    ){
        Column (modifier = Modifier.padding(bottom = DefaultTheme.dimens.grid_1_5)){
            AppHeader(action = {
                navController.navigate(Screen.SettingsScreen.route)
            })
            LoginHeader()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                CustomTextField(emailState, stringResource(R.string.email), bottomPadding = DefaultTheme.dimens.grid_2)
                CustomTextField(passwordState, "Contraseña", password = true)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = DefaultTheme.dimens.grid_1_5,
                            top = DefaultTheme.dimens.grid_1
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CheckBox(checkbox, stringResource(R.string.remember_me), action = {
                        checkbox.isCheck = !checkbox.isCheck
                    })
                    Text(
                        stringResource(R.string.forgot_password),
                        fontSize = 11.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable{
                            coroutineScope.launch {
                                navController.navigate(Screen.ForgotScreen.route)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(DefaultTheme.dimens.grid_3))
                Button(onClick = {
                    coroutineScope.launch {
                        //postUser(context, response)
                        getUser(context, response)
                        println("asdasds")
                    }

                }) {
                    Text("asdasd")
                }
                CustomButton(stringResource(R.string.login), action = {
                    if (emailState.text == "" || passwordState.text == "") {
                        snackbarText = "Por favor rellene todos los campos"
                        println("adsddsadsa")
                        snackbarColor = snackbarError
                    } else if (passwordState.text.length <= 5 ) {
                        snackbarText = "La contraseña no es valida"
                        snackbarColor = snackbarError
                    } else {
                        snackbarText = "Iniciando sesion..."
                        snackbarColor = Color.Green
                        navController.navigate(Screen.MainScreen.route)
                    }
                    if (showSnackbar){
                        showSnackbar = false
                        coroutineScope.launch {
                            var snackbarResult = snackbarHostState.showSnackbar(message = snackbarText, duration= SnackbarDuration.Short)
                            when (snackbarResult){
                                SnackbarResult.Dismissed -> showSnackbar = true
                                SnackbarResult.ActionPerformed -> showSnackbar = true
                            }
                        }
                    }
                })
                Division()
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(DefaultTheme.dimens.grid_7),
                    onClick = {navController.navigate(Screen.RegisterScreen.route)},
                ) {
                    Text(stringResource(R.string.create_an_account), color = Grey,)
                }
            }
        }
        BoxWithConstraints (
            Modifier
                .fillMaxSize()
                .padding(DefaultTheme.dimens.grid_2)){
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                beresident.prototype.beresidentuserapp.screens.shared.SnackbarHost(
                    snackbarHostState,
                    snackbarColor
                )
            }
        }
    }
}

private suspend fun getUser(
    context: Context,
    result: MutableState<String>,
) {
    var url = "http://192.168.11.117:2400"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val user = UserModel("test4@gmail.com", "wer123")

    val call: Call<UserModel> = apiService.login(user)

    call!!.enqueue(object : Callback<UserModel> {
        override fun onResponse(
            call: Call<UserModel>,
            response: Response<UserModel>
        ) {
            // on below line we are checking if response is successful.
            if (response.isSuccessful) {
                // on below line we are creating a new list
                var users: UserModel

                // on below line we are passing
                // our response to our list
                users = response.body()!!
                println(users)

                // on below line we are passing
                // data from lst to course list.
            }
        }

        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            // displaying an error message in toast
            Toast.makeText(context, "Fail to get the data..", Toast.LENGTH_SHORT)
                .show()
        }
    })
}



private fun postUser(
    context: Context,
    result: MutableState<String>,
) {
    var url = "http://192.168.11.117:2400"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
    val user = UserModel("test3@gmail.com", "wer123")
    val call: Call<UserModel> = apiService.register(user)

    call.enqueue(object: Callback<UserModel> {
        override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
            Toast.makeText(context, "Data posted to Api", Toast.LENGTH_SHORT).show()
            val model: UserModel? = response.body()
            val resp = "Response Code : " + response.code() + "\n" + "User Name : " + model!!.email + "\n" + "Job : " + model!!.password
            result.value = resp
            println(resp)
        }

        override fun onFailure(call: Call<UserModel>, t: Throwable) {
            result.value = "Error found in: " + t.message
        }
    })
}

