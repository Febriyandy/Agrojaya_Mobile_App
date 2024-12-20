package com.febriandi.agrojaya.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
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
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.data.firebase.Resource
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Halaman Ganti Password
@Composable
fun GantiPasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    onPasswordChangeSuccess: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    navController: NavController,
){
    val currentPassword by viewModel.currentPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val changePasswordState by viewModel.changePasswordState.collectAsState()

    var passwordVisibility by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(changePasswordState) {
        when (val state = changePasswordState) {
            is Resource.Success -> {
                viewModel.updateCurrentPassword("")
                viewModel.updateNewPassword("")
                viewModel.updateConfirmPassword("")

                Toast.makeText(context, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()

                onPasswordChangeSuccess()
                viewModel.resetState()
            }
            is Resource.Error -> {
                state.message?.let { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Header(navController, title = "Ganti Kata Sandi")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { viewModel.updateCurrentPassword(it)},
                    label = { Text(
                        text = "Kata Sandi Saat Ini",
                        fontFamily = CustomFontFamily
                    ) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisibility) R.drawable.icon_visibility_off else R.drawable.icon_visibility
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painterResource(id = icon),
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
                    value = newPassword,
                    onValueChange = { viewModel.updateNewPassword(it) },
                    label = { Text(
                        text = "Kata Sandi Baru",
                        fontFamily = CustomFontFamily
                    ) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisibility) R.drawable.icon_visibility_off else R.drawable.icon_visibility
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painterResource(id = icon),
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
                    value = confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it)},
                    label = { Text(
                        text = "Konfirmasi Kata Sandi",
                        fontFamily = CustomFontFamily
                    ) },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisibility) R.drawable.icon_visibility_off else R.drawable.icon_visibility
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painterResource(id = icon),
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
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (changePasswordState is Resource.Loading) {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.green_400),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    text = "Ubah Kata Sandi",
                    onClick = {
                        if (newPassword == confirmPassword) {
                            viewModel.changePassword()
                        } else {
                            Toast.makeText(context, "Konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                        }
                    },
                )


            }
        }
    }
}