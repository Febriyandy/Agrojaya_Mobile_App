package com.febriandi.agrojaya.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

//Halaman Lupa password
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navController: NavController,
    onResetSuccess: () -> Unit = {}
) {
    val email by viewModel.emailState.collectAsState()
    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(resetPasswordState) {
        when (val state = resetPasswordState) {
            is Resource.Success -> {
                Toast.makeText(
                    context,
                    "Email reset kata sandi telah dikirim",
                    Toast.LENGTH_LONG
                ).show()
                onResetSuccess()
                viewModel.resetState()
            }
            is Resource.Error -> {
                Toast.makeText(
                    context,
                    state.message ?: "Gagal mengirim email reset",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //Header
                Header(navController, title = "Lupa Kata Sandi")

                // Deskripsi
                Text(
                    text = "Masukkan email yang terdaftar. Kami akan mengirimkan instruksi untuk mereset kata sandi Anda.",
                    fontFamily = CustomFontFamily,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.text_color),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                )

                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = {
                        Text(
                            text = "Email",
                            fontFamily = CustomFontFamily
                        )
                    },
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Tombol Reset Kata Sandi
                ButtonComponent(
                    text = "Kirim",
                    onClick = {
                        if (email.isNotBlank()) {
                            viewModel.resetPassword()
                        } else {
                            Toast.makeText(
                                context,
                                "Silakan masukkan email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                )

            }
        }
    }
}