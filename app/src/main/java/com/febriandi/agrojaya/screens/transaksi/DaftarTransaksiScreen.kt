package com.febriandi.agrojaya.screens.transaksi

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.screens.transaksi.component.TransaksiItem
import com.febriandi.agrojaya.screens.transaksi.viewmodel.DaftarTransaksiViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman daftar transaksi
@Composable
fun DaftarTransaksiScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DaftarTransaksiViewModel = hiltViewModel()
){
    val transaksiState by viewModel.transaksiState.collectAsState()


    LaunchedEffect(key1 = Unit) {
        viewModel.loadTransaksis()
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ){
        Header(navController, "Pesanan Saya")

        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentState = transaksiState) {
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
                    val transaksis = currentState.data
                    if (transaksis.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = transaksis,
                                key = { it.id }
                            ) { transaksi ->
                                TransaksiItem(
                                    transaksi = transaksi,
                                    onItemClicked = { orderId ->
                                        navController.navigate("detailTransaksi/$orderId")
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
                                text = "Tidak ada Transaksi",
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
                            onClick = { viewModel.loadTransaksis() },
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

