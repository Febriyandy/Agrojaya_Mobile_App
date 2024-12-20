package com.febriandi.agrojaya.screens.alamat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman Alamat Screen
@Composable
fun AlamatScreen(
    navController: NavController,
    viewModel: AlamatViewModel = hiltViewModel()
){
    val alamatState by viewModel.alamatState.collectAsState()
    val alamatAddedFlag = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("alamat_added") ?: false

    val alamatUpdatedFlag = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("alamat_updated") ?: false


    LaunchedEffect(alamatUpdatedFlag) {
        if (alamatUpdatedFlag) {
            viewModel.loadAlamat()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("alamat_updated", false)
        }
    }

    LaunchedEffect(alamatAddedFlag) {
        if (alamatAddedFlag) {
            viewModel.loadAlamat()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("alamat_added", false)
        }
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                ButtonBack {
                    navController.popBackStack()
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = "Daftar Alamat",
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.text_color)
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .clickable {
                                navController.navigate("tambahAlamat")
                            },
                        text = "Tambah Alamat",
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.green_500)
                    )
                }

            }

        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentState = alamatState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.green_500)
                        )
                    }
                }
                is Resource.Success -> {
                    val alamats = currentState.data
                    if (alamats.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = alamats,
                                key = { it.id }
                            ) { alamat ->
                                AlamatItem(
                                    alamat = alamat,
                                    onItemClicked = { alamatId ->
                                        navController.navigate("ubahAlamat/$alamatId")
                                    }
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada Alamat tersedia",
                                fontFamily = CustomFontFamily
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = currentState.message ?: "Terjadi kesalahan",
                            color = Color.Red,
                            fontFamily = CustomFontFamily
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadAlamat() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green_500)
                            )
                        ) {
                            Text(
                                text = "Coba Lagi",
                                fontFamily = CustomFontFamily,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun AlamatScreenPreview(){
    AlamatScreen(navController = rememberNavController())
}
