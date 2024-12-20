package com.febriandi.agrojaya.screens.home.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.screens.artikel.ArtikelItem
import com.febriandi.agrojaya.screens.artikel.ArtikelViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Halaman hasil pencarian
@Composable
fun SearchResultScreen(
    rootNavController: NavController,
    navController: NavController,
    searchQuery: String,
    viewModel: ArtikelViewModel = hiltViewModel()
) {
    var search by remember { mutableStateOf(searchQuery) }
    val searchResultState by viewModel.searchResultState.collectAsState()
    var isSearchClicked by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        Log.d("SearchResultScreen", "Received search query: $searchQuery")
        if (searchQuery.isNotEmpty()) {
            viewModel.searchArtikelshome(searchQuery)
        }
    }


    LaunchedEffect(searchResultState) {
        Log.d("SearchResultScreen", "Search result count: ${searchResultState.size}")
    }


    LaunchedEffect(isSearchClicked) {
        if (isSearchClicked && search.isNotEmpty()) {
            viewModel.searchArtikelshome(search)
            isSearchClicked = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        HomeSearchField(
            search = search,
            onSearchChange = { search = it },
            onSearchClick = {
                if (search.isNotEmpty()) {
                    viewModel.searchArtikelshome(search)
                }
            }
        )

        Text(
            text = "Hasil pencarian untuk \"$search\"",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontFamily = CustomFontFamily,
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_color)
        )


        when (searchResultState.isEmpty()) {
            true -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = "Tidak ada hasil",
                            modifier = Modifier.size(100.dp),
                            tint = colorResource(id = R.color.green_500)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tidak ada artikel ditemukan",
                            fontFamily = CustomFontFamily,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_color)
                        )
                        Text(
                            text = "Coba kata kunci lain",
                            fontFamily = CustomFontFamily,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            false -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = searchResultState,
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
            }
        }
    }
}