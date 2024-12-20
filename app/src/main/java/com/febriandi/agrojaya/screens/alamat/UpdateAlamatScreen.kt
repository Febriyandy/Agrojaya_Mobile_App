package com.febriandi.agrojaya.screens.alamat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import android.widget.Toast
import com.febriandi.agrojaya.model.*
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.AlamatFormState
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.ErrorDialog
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.FormContent
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.LoadingOverlay
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.SubmitButton

//Halaman Update Alamat
@Composable
fun UpdateAlamatScreen(
    navController: NavController,
    alamatId: Int,
    viewModel: TambahAlamatViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var formState by remember {
        mutableStateOf(
            AlamatFormState(
                nama = "",
                noHp = "",
                provinsi = null,
                kabupaten = null,
                kecamatan = null,
                kelurahan = null,
                alamat = "",
                catatan = ""
            )
        )
    }


    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    val provinsiList by viewModel.provinsiList.collectAsState()
    val kabupatenList by viewModel.kabupatenList.collectAsState()
    val kecamatanList by viewModel.kecamatanList.collectAsState()
    val kelurahanList by viewModel.kelurahanList.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val existingAlamat by viewModel.existingAlamat.collectAsState()


    LaunchedEffect(alamatId) {
        viewModel.loadAlamatById(alamatId)
    }


    LaunchedEffect(existingAlamat) {
        existingAlamat?.let { alamat ->
            formState = AlamatFormState(
                nama = alamat.nama,
                noHp = alamat.noHp,
                provinsi = Provinsi(
                    id = alamat.provinsi,
                    nama = alamat.provinsi
                ),
                kabupaten = Kabupaten(
                    id = alamat.kabupaten,
                    provinsi_id = alamat.provinsi,
                    nama = alamat.kabupaten
                ),
                kecamatan = Kecamatan(
                    id = alamat.kecamatan,
                    kabupaten_id = alamat.kabupaten,
                    nama = alamat.kecamatan
                ),
                kelurahan = Kelurahan(
                    id = alamat.kelurahan,
                    kecamatan_id = alamat.kecamatan,
                    nama = alamat.kelurahan
                ),
                alamat = alamat.alamatLengkap,
                catatan = alamat.catatan
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitResult.collect { result ->
            result.fold(
                onSuccess = { response ->
                    if (response.success) {
                        navController.previousBackStackEntry?.savedStateHandle?.set("alamat_updated", true)
                        Toast.makeText(context, "Alamat Berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        errorMessage = response.message ?: "Gagal memperbarui alamat"
                        showErrorDialog = true
                    }
                },
                onFailure = { exception ->
                    errorMessage = exception.message ?: "Terjadi kesalahan"
                    showErrorDialog = true
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Header(navController, title = "Perbarui Alamat")

        // Form Content
        FormContent(
            formState = formState,
            onFormStateChange = { formState = it },
            provinsiList = provinsiList,
            kabupatenList = kabupatenList,
            kecamatanList = kecamatanList,
            kelurahanList = kelurahanList,
            onProvinsiSelected = { selectedProvinsi ->
                formState = formState.copy(
                    provinsi = selectedProvinsi,
                    kabupaten = null,
                    kecamatan = null,
                    kelurahan = null
                )
                viewModel.loadKabupaten(selectedProvinsi.id)
            },
            onKabupatenSelected = { selectedKabupaten ->
                formState = formState.copy(
                    kabupaten = selectedKabupaten,
                    kecamatan = null,
                    kelurahan = null
                )
                viewModel.loadKecamatan(selectedKabupaten.id)
            },
            onKecamatanSelected = { selectedKecamatan ->
                formState = formState.copy(
                    kecamatan = selectedKecamatan,
                    kelurahan = null
                )
                viewModel.loadKelurahan(selectedKecamatan.id)
            },
        )


        SubmitButton(
            isSubmitting = isSubmitting,
            formState = formState,
            onSubmit = {
                viewModel.updateAlamat(
                    id = alamatId,
                    nama = formState.nama,
                    noHp = formState.noHp,
                    provinsi = formState.provinsi?.nama ?: "",
                    kabupaten = formState.kabupaten?.nama ?: "",
                    kecamatan = formState.kecamatan?.nama ?: "",
                    kelurahan = formState.kelurahan?.nama ?: "",
                    alamatLengkap = formState.alamat,
                    catatan = formState.catatan
                )
            },
            onValidationError = {
                errorMessage = "Mohon lengkapi semua data"
                showErrorDialog = true
            }
        )
    }

    // Dialog dan Loading
    if (showErrorDialog && errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onDismiss = { showErrorDialog = false }
        )
    }

    if (isSubmitting) {
        LoadingOverlay()
    }
}

