package com.febriandi.agrojaya.screens.Paket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman Detail Paket
@Composable
fun DetailPaketScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    paketId: Int?,
    viewModel: DetailPaketViewModel = hiltViewModel()
) {
    val paketState = viewModel.paketState.collectAsState()

    LaunchedEffect(paketId) {
        paketId?.let { viewModel.loadPaket(it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = paketState.value) {
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
                DetailPaketContent(
                    paket = state.data,
                    onItemClicked = {
                        navController.navigate("pemesanan/$paketId")
                    },
                    navController = navController
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
                        onClick = { paketId?.let { viewModel.loadPaket(it) } },
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

//Detail paket content
@Composable
private fun DetailPaketContent(
    paket: PaketResponse,
    navController: NavController,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val categories = paket.variasi_bibit
        .replace("\"", "")
        .split(",")
        .map { it.trim() }

    val firstRowCategories = categories.take(categories.size / 2 + categories.size % 2)
    val secondRowCategories = categories.drop(categories.size / 2 + categories.size % 2)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header
            Header(navController, "Detail Paket")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(paket.photo)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentDescription = "Foto Paket"
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = paket.nama_paket,
                    fontSize = 16.sp,
                    style = TextStyle(lineHeight = 22.sp),
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = TextStyle(lineHeight = 22.sp),
                    text = "Rp ${String.format("%,d", paket.harga).replace(',', '.')}",
                    fontSize = 18.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.text_color)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = "Variasi Benih",
                    fontSize = 16.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .horizontalScroll(horizontalScrollState)
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                        ) {
                            firstRowCategories.forEach { category ->
                                CategoryItem(
                                    category = category,
                                    isSelected = false
                                )
                            }
                        }


                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            secondRowCategories.forEach { category ->
                                CategoryItem(
                                    category = category,
                                    isSelected = false
                                )
                            }
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    text = "Fitur",
                    fontSize = 16.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    paket.fitur
                        .replace("\"", "")
                        .split(",")
                        .map { it.trim() }
                        .forEach { fitur ->
                            Text(
                                text = "• $fitur",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = CustomFontFamily,
                                color = colorResource(id = R.color.text_color),
                                style = TextStyle(lineHeight = 25.sp)
                            )
                        }
                }

                Text(
                    modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 10.dp),
                    text = "Detail Paket",
                    fontSize = 16.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )

                Text(
                    text = paket.detail.replace("\"", ""),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 14.sp,
                    style = TextStyle(
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Justify
                    ),
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.text_color),
                )

                Spacer(modifier = Modifier.size(20.dp))

                ButtonComponent(
                    text = "Beli Sekarang",
                    onClick = { onItemClicked(paket.id) },

                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

//kategori item
@Composable
private fun CategoryItem(
    category: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.green_400),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (isSelected) colorResource(id = R.color.green_400)
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.White else colorResource(id = R.color.green_400),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = CustomFontFamily
        )
    }
}