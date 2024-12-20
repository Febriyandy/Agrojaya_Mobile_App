package com.febriandi.agrojaya.screens.artikel

import androidx.compose.foundation.background
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
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource

//Halaman Detail Artikel
@Composable
fun DetailArtikelScreen(
    navController: NavController,
    rootNavController: NavController,
    artikelId: Int?,
    viewModel: DetailArtikelViewModel = hiltViewModel()
) {
    val artikelState = viewModel.artikelState.collectAsState()

    LaunchedEffect(artikelId) {
        artikelId?.let { viewModel.loadArtikel(it) }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = artikelState.value) {
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
                DetailArtikelContent(
                    artikel = state.data,
                    navController
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
                        onClick = { artikelId?.let { viewModel.loadArtikel(it) } },
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
private fun DetailArtikelContent(
    artikel: ArtikelResponse,
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val paragraphs = remember(artikel.isi) {
        artikel.isi
            .replace("\\\\n\\\\n", "\n\n")
            .replace("\\\\n", "\n")
            .replace("\\n\\n", "\n\n")
            .replace("\\n", "\n")
            .replace("\"\\s*\\+\\s*\"".toRegex(), "")
            .split("\n\n")

            .flatMap { paragraph ->
                if (paragraph.contains("\n")) {
                    paragraph.split("\n").filter { it.isNotBlank() }
                } else {
                    listOf(paragraph)
                }
            }
            .filter { it.isNotBlank() }
            .map { it.trim() }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Header(navController, title = "Detail Artikel")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = artikel.judul,
                    fontSize = 20.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.text_color)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = artikel.penulis,
                    fontSize = 14.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.natural_400)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = artikel.tanggal,
                    fontSize = 14.sp,
                    fontFamily = CustomFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.natural_400)
                )

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = artikel.photo)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentDescription = "Foto Artikel"
                )

                paragraphs.forEachIndexed { index, paragraph ->
                    Text(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = if (index == 0) 8.dp else 16.dp,
                            bottom = 8.dp
                        ),
                        text = paragraph,
                        fontSize = 14.sp,
                        style = TextStyle(
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Justify
                        ),
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.text_color)
                    )
                }
            }
        }
    }
}