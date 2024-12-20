package com.febriandi.agrojaya.screens.Paket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman Paket Screen
@Composable
fun PaketScreen(
    navController: NavController,
    rootNavController: NavController,
    viewModel: PaketViewModel = hiltViewModel()
) {
    val paketState by viewModel.paketState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadPakets()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Daftar Paket",
            fontSize = 16.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.text_color)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentState = paketState) {
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
                    val pakets = currentState.data
                    if (pakets.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = pakets,
                                key = { it.id }
                            ) { paket ->
                                PaketItem(
                                    paket = paket,
                                    onItemClicked = { paketId ->
                                        rootNavController.navigate("detailPaket/$paketId")
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
                                text = "Tidak ada paket tersedia",
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
                            onClick = { viewModel.loadPakets() },
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