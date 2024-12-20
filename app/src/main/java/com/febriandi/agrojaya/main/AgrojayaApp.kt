package com.febriandi.agrojaya.main

/**
 * Kelas utama untuk mengatur navigasi di aplikasi Agrojaya.
 * Menggunakan Jetpack Compose Navigation untuk memfasilitasi navigasi antar halaman.
 */

import TambahAlamat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.febriandi.agrojaya.screens.register.AfterOnboarding
import com.febriandi.agrojaya.screens.onboarding.OnboardingScreen
import com.febriandi.agrojaya.screens.artikel.ArtikelScreen
import com.febriandi.agrojaya.screens.artikel.DetailArtikelScreen
import com.febriandi.agrojaya.screens.Paket.DetailPaketScreen
import com.febriandi.agrojaya.screens.login.LoginScreen
import com.febriandi.agrojaya.screens.register.RegisterScreen
import com.febriandi.agrojaya.screens.transaksi.TransaksiStatus
import com.febriandi.agrojaya.screens.alamat.AlamatScreen
import com.febriandi.agrojaya.screens.alamat.UpdateAlamatScreen
import com.febriandi.agrojaya.screens.home.component.SearchResultScreen
import com.febriandi.agrojaya.screens.login.ForgotPasswordScreen
import com.febriandi.agrojaya.screens.login.GantiPasswordScreen
import com.febriandi.agrojaya.screens.login.LoginViewModel
import com.febriandi.agrojaya.screens.notifikasi.NotifikasiScreen
import com.febriandi.agrojaya.screens.pembelian.PembelianScreen
import com.febriandi.agrojaya.screens.pengaturan.KebijakanPrivasiScreen
import com.febriandi.agrojaya.screens.pengaturan.KontakKamiScreen
import com.febriandi.agrojaya.screens.pengaturan.SyaratDanKetentuanScreen
import com.febriandi.agrojaya.screens.pengingat.JadwalAktivitasScreen
import com.febriandi.agrojaya.screens.pengingat.TambahPengingatScreen
import com.febriandi.agrojaya.screens.profile.EditProfileScreen
import com.febriandi.agrojaya.screens.transaksi.DaftarTransaksiScreen
import com.febriandi.agrojaya.screens.transaksi.DetailTransaksi
import com.febriandi.agrojaya.screens.transaksi.PaymentWebViewScreen

@Composable
fun AgrojayaApp(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isUserLoggedIn.collectAsState()
    val navController = rememberNavController()

// Jika pengguna sudah login, langsung navigasi ke "mainScreen"
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("mainScreen") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Deklarasi NavHost untuk mengatur navigasi
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        //Halaman Onboarding
        composable("onboarding") {
            OnboardingScreen(onFinished = {
                navController.navigate("afterOnboarding") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }


        //Halaman Login
        composable("login") {
            LoginScreen(navController)
        }

        //Halaman Register
        composable("register") {
            RegisterScreen(navController)
        }

        //Halaman Main Screen
        composable("mainScreen") {
            MainScreen(rootNavController = navController)
        }

        //Halaman Kontak Kami
        composable("kontakKami") {
            KontakKamiScreen(rootNavController = navController, context = LocalContext.current)
        }

        //Halaman Artikel
        composable("artikel") {
            ArtikelScreen(navController)
        }


        //Halaman alamat
        composable("alamat") {
            AlamatScreen(navController = navController)
        }

        //Halaman Daftar Transaksi
        composable("daftarTransaksi") {
            DaftarTransaksiScreen(
                navController = navController)
        }

        //Halaman Tambah alamat
        composable("tambahAlamat") {
            TambahAlamat(navController = navController)
        }

        //Halaman Ganti Password
        composable("gantiPassword") {
            GantiPasswordScreen(navController = navController)
        }

        //Halaman Edit Profile
        composable("editProfile") {
            EditProfileScreen(navController = navController)
        }

        //Halaman Lupa Password
        composable("lupaPassword") {
            ForgotPasswordScreen(navController = navController,
                onResetSuccess = {navController.popBackStack()})
        }

        //Halaman Setelah Onboarding
        composable("afterOnboarding") {
            AfterOnboarding(navController)
        }

        //Halaman Notifikasi
        composable("notifikasi") {
            NotifikasiScreen(navController = navController)
        }

        //Halaman Tambah Pengingat
        composable("tambahPengingat") {
            TambahPengingatScreen(navController = navController)
        }

        //Halaman Jadwal aktivitas
        composable("jadwalAktivitas") {
            JadwalAktivitasScreen(navController = navController)
        }

        //Halaman Syarat dan Ketentuan
        composable("syaratdanketentuan") {
            SyaratDanKetentuanScreen(rootNavController = navController)
        }

        //Halaman Kebijakan Privasi
        composable("kebijakanprivasi") {
            KebijakanPrivasiScreen(rootNavController = navController)
        }

        //Halaman Pemesanan
        composable(
            route = "pemesanan/{paketId}",
            arguments = listOf(
                navArgument("paketId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            PembelianScreen(
                onBackClick = { navController.navigateUp() },
                navController = navController,
                paketId = backStackEntry.arguments?.getInt("paketId")
            )
        }

        //Halaman Web View Pembayaran
        composable(
            route = "payment-webview/{paymentUrl}/{orderId}",
            arguments = listOf(
                navArgument("paymentUrl") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("orderId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val paymentUrl = backStackEntry.arguments?.getString("paymentUrl") ?: ""
            val orderId = backStackEntry.arguments?.getString("orderId") ?:""
                PaymentWebViewScreen(
                    navController = navController,
                    paymentUrl = paymentUrl,
                    orderId = orderId
                )
        }

        //Halaman Update Pengingat
        composable(
            "tambahPengingat/{pengingatId}",
            arguments = listOf(navArgument("pengingatId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pengingatId = backStackEntry.arguments?.getInt("pengingatId")
            TambahPengingatScreen(
                navController = navController,
                pengingatId = pengingatId
            )
        }

        //Halaman Hasil Pencarian
        composable(
            route = "hasilPencarian/{searchQuery}",
            arguments = listOf(
                navArgument("searchQuery") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""
            SearchResultScreen(
                navController = navController,
                rootNavController = navController,
                searchQuery = searchQuery
            )
        }

        //Halaman Detail Artikel
        composable(
            "detailArtikel/{artikelId}",
            arguments = listOf(
                navArgument("artikelId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            DetailArtikelScreen(
                rootNavController = navController,
                navController = navController,
                artikelId = backStackEntry.arguments?.getInt("artikelId")
            )
        }

        //Halaman Status Transaksi
        composable(
            "transaksi/{orderId}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            TransaksiStatus(
                rootNavController = navController,
                navController = navController,
                orderId = backStackEntry.arguments?.getString("orderId")
            )
        }

        //Halaman Detail Transaksi
        composable(
            "detailTransaksi/{orderId}",
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            DetailTransaksi(
                navController = navController,
                orderId = backStackEntry.arguments?.getString("orderId")
            )
        }

        //Halaman Ubah Alamat
        composable(
            "ubahAlamat/{alamatId}",
            arguments = listOf(
                navArgument("alamatId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("alamatId")?.let {
                UpdateAlamatScreen(
                    navController = navController,
                    alamatId = it
                )
            }
        }

        //Halaman Detail Paket
        composable(
            "detailPaket/{paketId}",
            arguments = listOf(
                navArgument("paketId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            DetailPaketScreen(
                navController = navController,
                paketId = backStackEntry.arguments?.getInt("paketId")
            )
        }
    }
}