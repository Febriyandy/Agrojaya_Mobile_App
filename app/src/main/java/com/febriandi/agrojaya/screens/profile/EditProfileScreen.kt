package com.febriandi.agrojaya.screens.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.model.UserResponse
import com.febriandi.agrojaya.screens.profile.component.ProfileEvent
import com.febriandi.agrojaya.screens.profile.component.UiEvent
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource
import kotlinx.coroutines.flow.collectLatest

//Halaman Edit Profile
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.userState.collectAsState()
    val state1 by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var username by remember(state) {
        mutableStateOf(
            when (state) {
                is Resource.Success -> (state as Resource.Success<UserResponse>).data.username
                else -> ""
            }
        )
    }

    var phoneNumber by remember(state) {
        mutableStateOf(
            when (state) {
                is Resource.Success -> (state as Resource.Success<UserResponse>).data.phoneNumber
                else -> ""
            }
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri == null) {
            Log.e("ImageSelection", "Tidak ada URI yang dipilih")
            return@rememberLauncherForActivityResult
        }

        try {
            Log.d("ImageSelection", "Dipilih URI: $uri")
            selectedImageUri = uri
            viewModel.onEvent(ProfileEvent.UpdateProfilePhoto(uri))
        } catch (e: Exception) {
            Log.e("ImageProcessing", "Kesalahan memproses URI: ${e.localizedMessage}")
        }
    }




    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvent.Success -> {
                    navController.popBackStack()
                }
                else -> {}
            }
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.loadUserData()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Header(navController, "Edit Profile")

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.green_400),
                            shape = CircleShape
                        )
                ) {
                    when {
                        selectedImageUri != null -> {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Foto profil terpilih",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        state1.profilePhotoUri != null -> {
                            AsyncImage(
                                model = state1.profilePhotoUri,
                                contentDescription = "Foto profil saat ini",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Foto profil default",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.green_400))
                        .border(1.dp, Color.White, CircleShape)
                        .align(Alignment.BottomCenter)
                        .clickable {
                            galleryLauncher.launch(arrayOf("image/*"))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_camera),
                        contentDescription = "Ubah foto",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        text = "Nama Lengkap",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))


            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = {
                    Text(
                        text = "Nomor Telepon",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (state1.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colorResource(id = R.color.green_400)
                    )
                }
            }

            ButtonComponent(
                onClick = {
                    viewModel.onEvent(
                        ProfileEvent.UpdateProfile(
                            username = username,
                            phoneNumber = phoneNumber
                        )
                    )
                },
                text = "Simpan Perubahan"
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}