package com.febriandi.agrojaya.screens.artikel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonBack
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource


@Composable
fun ArtikelScreen(
    navController: NavController,
    viewModel: ArtikelViewModel = hiltViewModel()
) {
    val artikelState by viewModel.artikelState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(navController, title = "Artikel")

        // Search section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Cari artikel menarik di sini",
                fontSize = 14.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.text_color)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newQuery ->
                    searchQuery = newQuery
                    viewModel.searchArtikels(newQuery)
                },
                label = null,
                placeholder = {
                    Text(
                        text = "Telusuri",
                        fontFamily = CustomFontFamily
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.green_300),
                    unfocusedBorderColor = colorResource(id = R.color.stroke_form),
                    focusedTextColor = colorResource(id = R.color.text_color),
                    unfocusedTextColor = colorResource(id = R.color.text_color),
                    focusedLabelColor = colorResource(id = R.color.green_300),
                    unfocusedLabelColor = colorResource(id = R.color.text_color),
                    cursorColor = colorResource(id = R.color.text_color),
                    unfocusedContainerColor = colorResource(id = R.color.green_100),
                    focusedContainerColor = colorResource(id = R.color.green_100)
                ),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.text_color),
                    fontFamily = CustomFontFamily
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 20.dp)
                    .height(50.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_search),
                        contentDescription = "Pencarian",
                        tint = colorResource(id = R.color.text_color),
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentState = artikelState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colorResource(id = R.color.green_500)
                    )
                }
                is Resource.Success<List<ArtikelResponse>> -> {
                    val artikels = currentState.data
                    if (artikels.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = artikels,
                                key = { it.id }
                            ) { artikel ->
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
                            text = currentState.message,
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
}