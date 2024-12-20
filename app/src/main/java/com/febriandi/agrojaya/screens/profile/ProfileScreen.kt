package com.febriandi.agrojaya.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.UserResponse
import com.febriandi.agrojaya.screens.login.LoginViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman Profile
@Composable
fun ProfileScreen(
    rootNavController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val profilePhotoUri by profileViewModel.profilePhotoUri.collectAsState()
    val userState by profileViewModel.userState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        profileViewModel.loadUserData()
        profileViewModel.loadProfilePhoto()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo3),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(170.dp)
                    .padding(top = 20.dp)
                    .align(Alignment.TopEnd)
            )
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, colorResource(id = R.color.green_400), CircleShape)
                    .padding(3.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profilePhotoUri)
                        .error(R.drawable.profile)
                        .fallback(R.drawable.profile)
                        .placeholder(R.drawable.profile)
                        .build(),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val userData = when (userState) {
                is Resource.Success -> (userState as Resource.Success<UserResponse>).data
                else -> null
            }

            Text(
                text = userData?.username ?: "Nama Pengguna",
                fontSize = 14.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_color)
            )

            Text(
                text = userData?.email ?: "email@example.com",
                fontSize = 12.sp,
                fontFamily = CustomFontFamily,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    rootNavController.navigate("editProfile")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green_400)
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.width(150.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_edit),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.green_100))
                .padding(12.dp)
                .height(50.dp)
                .clickable {
                    rootNavController.navigate("daftarTransaksi")
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_400),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Pesanan Saya",
                        color = colorResource(id = R.color.text_color),
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Medium
                    )

                        Text(
                            text = "Lihat transaksi anda di sini",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = CustomFontFamily
                        )

                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.green_100))
                .padding(12.dp)
                .height(50.dp)
                .clickable {
                    rootNavController.navigate("alamat")
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.loc),
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_400),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Alamat Pengguna",
                        color = colorResource(id = R.color.text_color),
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Atur alamat pemasangan urban farming",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontFamily = CustomFontFamily
                    )

                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pengaturan Akun",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = CustomFontFamily,
            color = colorResource(id = R.color.text_color)
        )

        Spacer(modifier = Modifier.height(12.dp))

        MenuButton(
            icon = R.drawable.lock,
            text = "Keamanan Akun",
            subtitle = "Ubah kata sandi",
            onClick = {
                rootNavController.navigate("gantiPassword")
            }
        )

        MenuButton(
            icon = R.drawable.doc,
            text = "Syarat dan Ketentuan",
            onClick = {
                rootNavController.navigate("syaratdanketentuan")
            }
        )

        MenuButton(
            icon = R.drawable.privacy,
            text = "Kebijakan Privasi",
            onClick = {
                rootNavController.navigate("kebijakanprivasi")
            }

        )

        MenuButton(
            icon = R.drawable.call,
            text = "Kontak Kami",
            onClick = {
                rootNavController.navigate("kontakKami")
            }
        )

        MenuButton(
            icon = R.drawable.logout,
            text = "Keluar Akun",
            onClick = {
                viewModel.signOut {
                    rootNavController.navigate("onboarding") {
                        popUpTo(rootNavController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        )


        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun MenuButton(
    icon: Int,
    text: String,
    subtitle: String? = null,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = colorResource(id = R.color.text_color),
    onClick: () -> Unit

) {
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(6.dp)
            .height(40.dp)
            .clickable(onClick = onClick)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = colorResource(id = R.color.green_400),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 14.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.Medium
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontFamily = CustomFontFamily
                    )
                }
            }
        }
    }
}

