package beresident.prototype.beresidentuserapp.screens.splash

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.core.services.StoreUserCredentials
import beresident.prototype.beresidentuserapp.core.services.ConnectionState
import beresident.prototype.beresidentuserapp.core.services.currentConnectivityState
import beresident.prototype.beresidentuserapp.core.services.observeConnectivityAsFlow
import beresident.prototype.beresidentuserapp.screens.splash.widgets.SplashView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SplashScreen(navController: NavController){
    val connection by connectivityState() //Declares our connection state
    val isConnected = connection === ConnectionState.Available //Sets our connection available as default

    //Gets our data store values
    val userStore = StoreUserCredentials(LocalContext.current)
    val userEmail = userStore.getEmail.collectAsState(initial = "")
    val userPassword = userStore.getPassword.collectAsState(initial = "")

    //Saves our camera permission and launches activity data
    val camera = rememberLauncherForActivityResult(ActivityResultContracts.
    RequestPermission()) {
        if (isConnected) {
            if (userEmail.value != "" && userPassword.value != "") {
                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.SplashScreen.route){
                        inclusive = true
                    }
                }
            }else {
                navController.navigate(Screen.LoginScreen.route){
                    popUpTo(Screen.SplashScreen.route){
                        inclusive = true
                    }
                }
            }
        } else {
            navController.navigate(Screen.NoInternet.route)
        }
    }

    //Saves our app write permissions
    val write = rememberLauncherForActivityResult(ActivityResultContracts.
    RequestPermission()) {
        camera.launch(Manifest.permission.CAMERA) //We ask for camera permissions
    }

    //Saves our read permissions
    val read = rememberLauncherForActivityResult(ActivityResultContracts.
    RequestPermission()) {
        write.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE) //We ask for writing permissions
    }

    //When our app changes, we ask for reading permissions
    LaunchedEffect(key1 = true, block = {
        read.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    })

    //Main view
    SplashView()
}

//We get our connection state
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    //Gets our current app context
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collect { value = it }
    }
}