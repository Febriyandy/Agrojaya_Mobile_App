package com.febriandi.agrojaya.screens.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.screens.artikel.ArtikelItem
import com.febriandi.agrojaya.screens.artikel.ArtikelViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Artikel Header home
@Composable
fun HomeArtikelHeader(rootNavController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 20.dp)
    ) {
        Text(
            text = "Artikel",
            fontSize = 14.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.green_500)
        )

        Text(
            modifier = Modifier.clickable {
                rootNavController.navigate("artikel")
            },
            text = "Lihat Semua",
            fontSize = 14.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.green_500)
        )
    }
}

@Composable
fun HomeArtikelContent(
    artikelState: Resource<List<ArtikelResponse>>,
    navController: NavController,
    viewModel: ArtikelViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (artikelState) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colorResource(id = R.color.green_500)
                )
            }
            is Resource.Success -> {
                val artikels = artikelState.data.take(3)

                if (artikels.isNotEmpty()) {
                    Column {
                        artikels.forEach { artikel ->
                            ArtikelItem(
                                artikel = artikel,
                                onItemClicked = { artikelId ->
                                    navController.navigate("detailArtikel/$artikelId")
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Tidak ada artikel",
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = CustomFontFamily
                    )
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
                        text = artikelState.message ?: "Terjadi kesalahan",
                        color = Color.Red,
                        fontFamily = CustomFontFamily
                    )
                    Button(
                        onClick = { viewModel.loadArtikels() },
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