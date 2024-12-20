import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.febriandi.agrojaya.screens.alamat.TambahAlamatViewModel
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.AlamatFormState
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.ErrorDialog
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.FormContent
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.LoadingOverlay
import com.febriandi.agrojaya.screens.alamat.tambahAlamat.SubmitButton
import android.widget.Toast

//Halaman Tambah Alamat
@Composable
fun TambahAlamat(
    navController: NavController,
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


    LaunchedEffect(Unit) {
        viewModel.submitResult.collect { result ->
            result.fold(
                onSuccess = { response ->
                    if (response.success) {
                        navController.previousBackStackEntry?.savedStateHandle?.set("alamat_added", true)
                        Toast.makeText(context, "Alamat Berhasil disimpan", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        errorMessage = response.message ?: "Gagal menyimpan alamat"
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
        Header(navController, title = "Tambah Alamat")

        // Form Content
        FormContent(
            formState = formState,
            onFormStateChange = { formState = it },
            provinsiList = provinsiList,
            kabupatenList = kabupatenList,
            kecamatanList = kecamatanList,
            kelurahanList = kelurahanList,
            onProvinsiSelected = { viewModel.loadKabupaten(it.id) },
            onKabupatenSelected = { viewModel.loadKecamatan(it.id) },
            onKecamatanSelected = { viewModel.loadKelurahan(it.id) }
        )

        // Submit Button
        SubmitButton(
            isSubmitting = isSubmitting,
            formState = formState,
            onSubmit = {
                viewModel.simpanAlamat(
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

    // Dialogs and Loading
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
