package com.febriandi.agrojaya.screens.register

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.febriandi.agrojaya.R
import androidx.compose.foundation.border
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.screens.login.LoginViewModel
import com.febriandi.agrojaya.utils.Constant.CLIENT
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import androidx.compose.material3.Text as Text

//Halaman setelah onboarding
@Composable
fun AfterOnboarding(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState(initial = null)

    @Suppress("DEPRECATION")
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(result.idToken, null)

                viewModel.loginWithGoogle(credential) {
                    navController.navigate("mainScreen") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                }
            } catch (it: ApiException) {
                Toast.makeText(context, "Google sign-in failed: $it", Toast.LENGTH_SHORT).show()
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(150.dp))

        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 90.dp),
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.size(60.dp))

        ButtonComponent(
            text = "Masuk Akun",
            onClick = {
                navController.navigate("login")
            }
        )
        Spacer(modifier = Modifier.size(20.dp))
        ButtonComponent(
            text = "Daftar Akun",
            onClick = {
                navController.navigate("register")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                color = colorResource(id = R.color.natural_500),
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp, 0.dp, 0.dp, 0.dp)
            )
            Text(
                text = "Atau Masuk Dengan",
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(
                color = colorResource(id = R.color.natural_500),
                thickness = 1.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 30.dp, 0.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))
        if (state.value?.loading == true) {
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(
                color = colorResource(id = R.color.green_400),
                modifier = Modifier.size(40.dp)
            )
        }

        Button(
            onClick = {
                val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(CLIENT)
                    .build()

                @Suppress("DEPRECATION")
                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                launcher.launch(googleSignInClient.signInIntent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.natural_200),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Google Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Masuk Dengan Google",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color)
            )
        }
    }
    LaunchedEffect(key1 = state.value?.success) {
        coroutineScope.launch {
            if (state.value?.success?.isNotEmpty() == true) {
                val success = state.value?.success
                Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()
            }
        }
    }
    LaunchedEffect(key1 = state.value?.error) {
        coroutineScope.launch {
            if (state.value?.error?.isNotEmpty() == true) {
                val error = state.value?.error
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AfterOnboardingPreview() {
    val navController = rememberNavController()
    AfterOnboarding(navController = navController)
}
