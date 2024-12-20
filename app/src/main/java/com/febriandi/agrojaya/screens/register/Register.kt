package com.febriandi.agrojaya.screens.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.screens.login.LoginViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import kotlinx.coroutines.launch

//Halaman Register
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState(initial = null)
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confPasswordVisible by remember { mutableStateOf(false) }


    Column (
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
        ButtonBack {
            navController.navigate("login")
        }
        Spacer(
            modifier = Modifier.size(50.dp,)
        )

        Text(
            text = "Halo Daftar Sekarang \nUntuk Memulai",
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 20.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.text_color)
        )

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(230.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(
                text = "Masukkan Username",
                fontFamily = CustomFontFamily
            ) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.green_100),
                unfocusedBorderColor = colorResource(id = R.color.stroke_form),
                focusedTextColor = colorResource(id = R.color.text_color),
                unfocusedTextColor = colorResource(id = R.color.text_color),
                focusedLabelColor = colorResource(id = R.color.green_500),
                unfocusedLabelColor = colorResource(id = R.color.text_color),
                cursorColor = colorResource(id = R.color.text_color),
                unfocusedContainerColor = colorResource(id = R.color.fill_form),
                focusedContainerColor = colorResource(id = R.color.fill_form)
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color),
                fontFamily = CustomFontFamily
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(
                text = "Masukkan Alamat Email",
                fontFamily = CustomFontFamily
            ) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.green_100),
                unfocusedBorderColor = colorResource(id = R.color.stroke_form),
                focusedTextColor = colorResource(id = R.color.text_color),
                unfocusedTextColor = colorResource(id = R.color.text_color),
                focusedLabelColor = colorResource(id = R.color.green_500),
                unfocusedLabelColor = colorResource(id = R.color.text_color),
                cursorColor = colorResource(id = R.color.text_color),
                unfocusedContainerColor = colorResource(id = R.color.fill_form),
                focusedContainerColor = colorResource(id = R.color.fill_form)
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color),
                fontFamily = CustomFontFamily
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp)
        )


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(
                text = "Masukkan Kata Sandi",
                fontFamily = CustomFontFamily
            ) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) R.drawable.icon_visibility_off else R.drawable.icon_visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painterResource(id = icon),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.green_100),
                unfocusedBorderColor = colorResource(id = R.color.stroke_form),
                focusedTextColor = colorResource(id = R.color.text_color),
                unfocusedTextColor = colorResource(id = R.color.text_color),
                focusedLabelColor = colorResource(id = R.color.green_500),
                unfocusedLabelColor = colorResource(id = R.color.text_color),
                cursorColor = colorResource(id = R.color.text_color),
                unfocusedContainerColor = colorResource(id = R.color.fill_form),
                focusedContainerColor = colorResource(id = R.color.fill_form)
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color),
                fontFamily = CustomFontFamily
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp)
        )

        OutlinedTextField(
            value = confPassword,
            onValueChange = { confPassword = it },
            label = { Text(
                text = "Konfirmasi Kata Sandi",
                fontFamily = CustomFontFamily
            ) },
            singleLine = true,
            visualTransformation = if (confPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confPasswordVisible) R.drawable.icon_visibility_off else R.drawable.icon_visibility
                IconButton(onClick = { confPasswordVisible = !confPasswordVisible }) {
                    Icon(painterResource(id = icon),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.green_100),
                unfocusedBorderColor = colorResource(id = R.color.stroke_form),
                focusedTextColor = colorResource(id = R.color.text_color),
                unfocusedTextColor = colorResource(id = R.color.text_color),
                focusedLabelColor = colorResource(id = R.color.green_500),
                unfocusedLabelColor = colorResource(id = R.color.text_color),
                cursorColor = colorResource(id = R.color.text_color),
                unfocusedContainerColor = colorResource(id = R.color.fill_form),
                focusedContainerColor = colorResource(id = R.color.fill_form)
            ),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = colorResource(id = R.color.text_color),
                fontFamily = CustomFontFamily
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp)
        )

        if (state.value?.loading == true) {
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(
                color = colorResource(id = R.color.green_400),
                modifier = Modifier.size(40.dp)
            )
        }



        Spacer(modifier = Modifier.size(25.dp))
        ButtonComponent(
            text = "Daftar",
            onClick = {
                coroutineScope.launch {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Email dan Password Wajib Diisi",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else if (password != confPassword) {
                        Toast.makeText(
                            context,
                            "Password dan Konfirmasi Password tidak cocok",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        viewModel.registerUser(username, email, password) {
                            navController.navigate("mainScreen") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                            username = ""
                            email = ""
                            password = ""
                            confPassword = ""
                        }
                    }
                }
            }
        )



        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sudah punya akun?",
                fontSize = 14.sp,
                fontFamily = CustomFontFamily
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Login Sekarang",
                color = colorResource(id = R.color.green_400),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = CustomFontFamily,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
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
}

