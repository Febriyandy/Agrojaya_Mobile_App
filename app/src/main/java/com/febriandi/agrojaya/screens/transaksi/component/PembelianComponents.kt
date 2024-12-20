package com.febriandi.agrojaya.screens.transaksi.component

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.screens.transaksi.viewmodel.TransaksiViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//section alamat
@Composable
fun PembelianAlamatSection(
    navController: NavController,
    alamatState: Resource<List<AlamatResponse>>,
    onAlamatSelect: (AlamatResponse?) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painter = painterResource(id = R.drawable.loc),
                contentDescription = "Icon",
                modifier = Modifier.size(25.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.text_color))
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Alamat Pengiriman",
                fontSize = 14.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.text_color)
            )

        }
        Image(
            painter = painterResource(id = R.drawable.icon_tambah),
            contentDescription = "Icon",
            modifier = Modifier.size(30.dp)
                .clickable { navController.navigate("tambahAlamat") },
            colorFilter = ColorFilter.tint(colorResource(id = R.color.green_400))
        )

    }

    when (alamatState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.green_500)
                )
            }
        }
        is Resource.Success -> {
            val alamats = alamatState.data
            if (alamats.isNotEmpty()) {
                var selectedAlamat by remember { mutableStateOf<AlamatResponse?>(null) }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = alamats,
                        key = { it.id }
                    ) { alamat ->
                        AlamatCard(
                            alamat = alamat,
                            isSelected = selectedAlamat?.id == alamat.id,
                            onSelect = {
                                selectedAlamat = if (selectedAlamat?.id == alamat.id) null else alamat
                                onAlamatSelect(selectedAlamat)
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
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
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = alamatState.message ?: "Terjadi kesalahan",
                    color = Color.Red,
                    fontFamily = CustomFontFamily
                )
            }
        }
    }
}

//section detail paket
@Composable
fun PembelianPaketDetails(paket: PaketResponse) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        text = paket.nama_paket,
        fontSize = 16.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.text_color)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = paket.photo)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentDescription = "Foto Paket"
        )
        Text(
            text = "Harga : Rp ${String.format("%,d", paket.harga).replace(',', '.')} \nTotal harga sudah termasuk biaya keseluruhan pemasangan.",
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = CustomFontFamily,
            color = colorResource(id = R.color.text_color),
            style = TextStyle(lineHeight = 25.sp)
        )
    }
}

//section varian bibit
@Composable
fun PembelianVarianBibitSelection(
    paket: PaketResponse,
    categories: List<String>,
    selectedCategories: MutableList<String>
) {
    val context = LocalContext.current

    val maxSelection = when {
        paket.nama_paket.contains("Dasar", ignoreCase = true) -> 1
        paket.nama_paket.contains("Menengah", ignoreCase = true) -> 3
        paket.nama_paket.contains("Lengkap", ignoreCase = true) -> 5
        paket.nama_paket.contains("Premium", ignoreCase = true) -> 10
        else -> 1
    }

    Text(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        text = "Pilih Varian Bibit (Maksimal $maxSelection)",
        fontSize = 14.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.text_color)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(categories) { category ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedCategories.contains(category),
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            if (selectedCategories.size < maxSelection) {
                                selectedCategories.add(category)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Maksimal pemilihan varian untuk paket ini adalah $maxSelection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            selectedCategories.remove(category)
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.green_400),
                        uncheckedColor = Color.Gray
                    )
                )

                Text(
                    text = category,
                    fontSize = 16.sp,
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

//component button pembayaran
@Composable
fun PembelianBottomSection(
    paket: PaketResponse,
    selectedAlamat: AlamatResponse?,
    selectedCategories: List<String>,
    navController: NavController,
    transaksiViewModel: TransaksiViewModel
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(color = colorResource(id = R.color.green_50))
                .align(Alignment.BottomCenter)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Total",
                        fontSize = 16.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(lineHeight = 20.sp),
                        color = colorResource(id = R.color.green_400)
                    )
                    Text(
                        text = "Rp ${String.format("%,d", paket.harga).replace(',', '.')}",
                        fontSize = 18.sp,
                        style = TextStyle(lineHeight = 20.sp),
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.text_color)
                    )
                }
                Button(
                    onClick = {
                        when {
                            selectedAlamat == null || selectedCategories.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    "Silahkan pilih alamat dan varian bibit",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                transaksiViewModel.createTransaction(
                                    paketId = paket.id,
                                    namaPaket = paket.nama_paket,
                                    alamatId = selectedAlamat.id,
                                    totalHarga = paket.harga,
                                    variasiBibit = selectedCategories.joinToString(",")
                                )
                            }
                        }
                    },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green_400)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Bayar",
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            val paymentState = transaksiViewModel.paymentState.collectAsState()

            when (val state = paymentState.value) {
                is Resource.Success -> {
                    LaunchedEffect(state) {
                        navController.navigate(
                            "payment-webview/${Uri.encode(state.data.data.snap_redirect_url)}/${Uri.decode(state.data.data.order_id)}"
                        )
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        state.message ?: "Terjadi kesalahan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                else -> {}
            }
        }
    }
}