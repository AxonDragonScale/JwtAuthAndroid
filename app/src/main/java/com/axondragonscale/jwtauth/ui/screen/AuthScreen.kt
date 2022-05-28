package com.axondragonscale.jwtauth.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.axondragonscale.jwtauth.auth.AuthResult
import com.axondragonscale.jwtauth.data.AuthUiEvent
import com.axondragonscale.jwtauth.ui.screen.destinations.AuthScreenDestination
import com.axondragonscale.jwtauth.ui.screen.destinations.SecretScreenDestination
import com.axondragonscale.jwtauth.vm.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

/**
 * Created by Ronak Harkhani on 28/05/22
 */

@Composable
@Destination(start = true)
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect {
            when (it) {
                is AuthResult.Authorized -> {
                    navigator.navigate(SecretScreenDestination) {
                        popUpTo(AuthScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "You are not Authorized", Toast.LENGTH_SHORT).show()
                }
                is AuthResult.UknownError -> {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.state.signUpUsername,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewModel.state.signUpPassword,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onEvent(AuthUiEvent.SignUp) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(64.dp))

        TextField(
            value = viewModel.state.signInUsername,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewModel.state.signInPassword,
            onValueChange = { viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onEvent(AuthUiEvent.SignIn) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
    }
    if (viewModel.state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}