package com.febriandi.agrojaya.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.febriandi.agrojaya.screens.profile.ProfileViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Home Header component
@Composable
fun HomeHeader(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    rootNavController: NavController
) {
    val userState by profileViewModel.userState.collectAsState()
    val profilePhotoUri by profileViewModel.profilePhotoUri.collectAsState()


    LaunchedEffect(Unit) {
        profileViewModel.loadUserData()
        profileViewModel.loadProfilePhoto()
    }


    Column {
        // Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
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

        // Profile dan Notifikasi
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
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

                val userData = when (userState) {
                    is Resource.Success -> (userState as Resource.Success<UserResponse>).data
                    else -> null
                }

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Hi, ${userData?.username ?: "User"}",
                    fontSize = 14.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )
            }

            Button(
                onClick = { rootNavController.navigate("notifikasi") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green_400)
                ),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_notifikasi),
                    contentDescription = "Notifikasi Icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
