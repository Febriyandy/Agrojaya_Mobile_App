package com.febriandi.agrojaya.screens.pembelian

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.screens.alamat.AlamatViewModel
import com.febriandi.agrojaya.screens.Paket.DetailPaketViewModel
import com.febriandi.agrojaya.screens.transaksi.component.PembelianScreenContent

//Halaman Pembelian Screen
@Composable
fun PembelianScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    paketId: Int?,
    viewModel: DetailPaketViewModel = hiltViewModel(),
    alamatViewModel: AlamatViewModel = hiltViewModel()
) {
    val paketState = viewModel.paketState.collectAsState()
    val alamatState by alamatViewModel.alamatState.collectAsState()

    val alamatAddedFlag = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("alamat_added") ?: false

    LaunchedEffect(paketId) {
        paketId?.let { viewModel.loadPaket(it) }
        alamatViewModel.loadAlamat()
    }

    LaunchedEffect(alamatAddedFlag) {
        if (alamatAddedFlag) {
            alamatViewModel.loadAlamat()
            // Reset flag
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("alamat_added", false)
        }
    }

    PembelianScreenContent(
        paketState = paketState.value,
        alamatState = alamatState,
        onBackClick = { navController.navigateUp() },
        navController = navController,
        onRetry = { paketId?.let { viewModel.loadPaket(it) } }
    )
}