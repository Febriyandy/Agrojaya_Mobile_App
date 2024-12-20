package com.febriandi.agrojaya.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.screens.home.component.PaketCarousel
import com.febriandi.agrojaya.screens.Paket.PaketViewModel
import com.febriandi.agrojaya.screens.artikel.ArtikelViewModel
import com.febriandi.agrojaya.screens.home.component.HomeAktivitasComponent
import com.febriandi.agrojaya.screens.home.component.HomeArtikelContent
import com.febriandi.agrojaya.screens.home.component.HomeArtikelHeader
import com.febriandi.agrojaya.screens.home.component.HomeHeader
import com.febriandi.agrojaya.screens.home.component.HomeSearchField
import com.febriandi.agrojaya.screens.home.component.HomeScheduleSection
import com.febriandi.agrojaya.screens.pengingat.PengingatViewModel
import com.febriandi.agrojaya.utils.Resource
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//Halaman Home Screen
@Composable
fun HomeScreen(
    navController: NavController,
    rootNavController: NavController,
    viewModel: ArtikelViewModel = hiltViewModel(),
    paketViewModel: PaketViewModel = hiltViewModel(),
    pengingatViewModel: PengingatViewModel = hiltViewModel()
) {
    var search by remember { mutableStateOf("") }
    var isSearchClicked by remember { mutableStateOf(false) }
    val artikelState by viewModel.artikelState.collectAsState()
    val paketState by paketViewModel.paketState.collectAsState()
    val pengingatState by pengingatViewModel.daftarPengingat.collectAsState()

    LaunchedEffect(isSearchClicked) {
        if (isSearchClicked && search.isNotEmpty()) {
            val encodedSearch = URLEncoder.encode(search, StandardCharsets.UTF_8.toString())

            rootNavController.navigate("hasilPencarian/$encodedSearch") {
                popUpTo(rootNavController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            isSearchClicked = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                // Carousel
                item {
                    when (paketState) {
                        is Resource.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                color = colorResource(id = R.color.green_500)
                            )
                        }
                        is Resource.Success -> {
                            val paketList = (paketState as Resource.Success).data.take(3)
                            if (paketList.isNotEmpty()) {
                                PaketCarousel(
                                    paketList = paketList,
                                    onItemClicked = { paketId ->
                                        rootNavController.navigate("detailPaket/$paketId")
                                    }
                                )
                            }
                        }
                        is Resource.Error -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Gagal memuat paket",
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Button(
                                    onClick = { paketViewModel.loadPakets() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(id = R.color.green_500)
                                    )
                                ) {
                                    Text("Coba Lagi")
                                }
                            }
                        }
                    }
                }

                // Jadwal Kegiatan
                item {
                    if (pengingatState.isNotEmpty()) {
                        HomeAktivitasComponent(
                            rootNavController = rootNavController,
                            viewModel = pengingatViewModel
                        )
                    } else {
                        HomeScheduleSection(rootNavController)
                    }
                }

                // Artikel Header
                item {
                    HomeArtikelHeader(rootNavController)
                }

                // Artikel Section
                item {
                    HomeArtikelContent(
                        artikelState = artikelState,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        },
        topBar = {
            Column(
                modifier = Modifier.background(Color.White)
            ) {
                //Home Header
                HomeHeader(
                    rootNavController = rootNavController
                )
                //Pencarian
                HomeSearchField(
                    search = search,
                    onSearchChange = { search = it },
                    onSearchClick = {
                        isSearchClicked = true
                    }
                )
            }
        }
    )
}