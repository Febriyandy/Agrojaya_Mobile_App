package com.febriandi.agrojaya.screens.transaksi


import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.screens.transaksi.component.OrderStatusItem
import com.febriandi.agrojaya.screens.transaksi.viewmodel.DetailTransaksiViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman detail transaksi
@Composable
fun DetailTransaksi(
    navController: NavController,
    modifier: Modifier = Modifier,
    orderId: String?,
    viewModel: DetailTransaksiViewModel = hiltViewModel()
) {
    val transaksiState = viewModel.transaksiState.collectAsState()

    LaunchedEffect (orderId) {
        orderId?.let { viewModel.loadTransaksi(it) }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = transaksiState.value) {
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
                DetailTransaksiContent(
                    transaksi = state.data,
                    navController = navController,
                    onItemCliked = {
                        navController.navigate(
                            "payment-webview/${Uri.encode(state.data.snap_redirect_url)}/${Uri.decode(state.data.order_id)}"
                        )
                    }
                )
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
                        text = state.message ?: "Terjadi kesalahan",
                        color = Color.Red,
                        fontFamily = CustomFontFamily
                    )
                    Button(
                        onClick = { orderId?.let { viewModel.loadTransaksi(it) } },
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

@Composable
fun DetailTransaksiContent(
    transaksi: TransaksiResponse,
    navController: NavController,
    onItemCliked: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var isStatusExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Header(navController, "Detail Pesanan")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_50)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_payment),
                            contentDescription = "Payment",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF4CAF50))
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(
                                text = transaksi.status_pembayaran,
                                fontSize = 16.sp,
                                fontFamily = CustomFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.text_color)
                            )
                            Text(
                                text = "Rp ${
                                    String.format("%,d", transaksi.total_harga).replace(',', '.')
                                }",
                                fontSize = 16.sp,
                                fontFamily = CustomFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.text_color)
                            )
                        }
                    }
                }


                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_50))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.loc),
                                contentDescription = "Location",
                                modifier = Modifier.size(16.dp),
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.text_color))
                            )
                            Text(
                                text = "${transaksi.nama_alamat} (${transaksi.noHp})",
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 14.sp,
                                fontFamily = CustomFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.text_color)
                            )
                        }
                        Text(
                            text = buildString {
                                append(transaksi.alamatLengkap)
                                append(", ")
                                append("Kelurahan ${transaksi.kelurahan}")
                                append(", ")
                                append("Kecamatan ${transaksi.kecamatan}")
                                append(", ")
                                append(transaksi.kabupaten)
                                append(", ")
                                append("Provinsi ${transaksi.provinsi}")
                            },
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = CustomFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = R.color.text_color)
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = transaksi.nama_paket,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = CustomFontFamily,
                                color = colorResource(id = R.color.text_color)
                            )
                            Text(
                                text = transaksi.tanggal,
                                fontSize = 14.sp,
                                fontFamily = CustomFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.text_color)
                            )
                        }

                        Row() {
                            AsyncImage(
                                model = transaksi.photo_paket,
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = "Variasi Bibit",
                                    fontSize = 14.sp,
                                    fontFamily = CustomFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(id = R.color.text_color)
                                )
                                Text(
                                    text = transaksi.variasi_bibit,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = CustomFontFamily,
                                    style = TextStyle(
                                        lineHeight = 15.sp
                                    ),
                                    color = colorResource(id = R.color.text_color)

                                )
                                Text(
                                    text = "Rp ${
                                        String.format("%,d", transaksi.total_harga)
                                            .replace(',', '.')
                                    }",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = CustomFontFamily,
                                    color = colorResource(id = R.color.text_color)
                                )
                            }
                        }

                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isStatusExpanded = !isStatusExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Status Pesanan",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = CustomFontFamily,
                                color = colorResource(id = R.color.text_color)
                            )
                            Icon(
                                imageVector = if (isStatusExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = colorResource(id = R.color.text_color)
                            )
                        }

                        AnimatedVisibility(visible = isStatusExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                OrderStatusItem(
                                    status = "Menunggu Pembayaran",
                                    currentStatus = transaksi.status_transaksi
                                )
                                OrderStatusItem(
                                    status = "Pesanan Dibuat",
                                    currentStatus = transaksi.status_transaksi
                                )
                                OrderStatusItem(
                                    status = "Teknisi Menuju Lokasi Anda",
                                    currentStatus = transaksi.status_transaksi
                                )
                                OrderStatusItem(
                                    status = "Proses Pemasangan",
                                    currentStatus = transaksi.status_transaksi
                                )
                                OrderStatusItem(
                                    status = "Pesanan Selesai",
                                    currentStatus = transaksi.status_transaksi
                                )
                            }
                        }
                    }
                }

            }

            if (transaksi.status_pembayaran == "Menunggu Pembayaran") {
                ButtonComponent(
                    text = "Lihat Kode Pembayaran",
                    onClick = { onItemCliked(transaksi.snap_redirect_url) },
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}
