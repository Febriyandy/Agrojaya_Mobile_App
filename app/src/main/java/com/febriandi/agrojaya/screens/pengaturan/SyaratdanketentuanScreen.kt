package com.febriandi.agrojaya.screens.pengaturan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Halaman Syarat dan Ketentuan
@Composable
fun SyaratDanKetentuanScreen(
    rootNavController: NavController
) {
    val scrollState = rememberScrollState()

    val termsText = buildAnnotatedString {
        append("Selamat datang di Agrojaya! Aplikasi ini menyediakan layanan pemasangan urban farming yang membantu Anda menciptakan solusi pertanian modern di lingkungan perkotaan. Dengan menggunakan aplikasi kami, Anda menyetujui Syarat dan Ketentuan berikut: \n\n")

        sections.forEach { (header, content) ->
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("$header\n")
            }
            append("$content\n\n")
        }

        append("\nDengan menggunakan aplikasi Agrojaya, Anda dianggap telah membaca, memahami, dan menyetujui seluruh Syarat dan Ketentuan ini.")
        append("Jika Anda memiliki pertanyaan lebih lanjut, silakan hubungi layanan pelanggan kami melalui aplikasi atau email resmi Agrojaya.")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            rootNavController,
            title = "Syarat dan Ketentuan"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Syarat dan Ketentuan Penggunaan Aplikasi Agrojaya",
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 16.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_color)
            )

            Text(
                text = termsText,
                fontSize = 14.sp,
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.text_color),
                lineHeight = 20.sp
            )
        }
    }
}

private val sections = listOf(
    "1. Definisi" to """
        Aplikasi Agrojaya: Platform digital yang menyediakan layanan pemasangan dan konsultasi urban farming.
        Pengguna: Individu atau badan hukum yang menggunakan layanan Agrojaya.
        Layanan: Jasa pemasangan urban farming, termasuk instalasi, konsultasi, dan perawatan sistem.
    """.trimIndent(),

    "2. Persyaratan Pengguna" to """
        Pengguna wajib berusia minimal 18 tahun atau memiliki izin dari wali sah jika di bawah umur.
        Pengguna bertanggung jawab atas keakuratan informasi yang diberikan saat mendaftar atau memesan layanan.
    """.trimIndent(),

    "3. Ketentuan Pemesanan dan Pembayaran" to """
        Pemesanan layanan dilakukan melalui aplikasi Agrojaya dan dianggap sah setelah konfirmasi dari pihak kami.
        Biaya layanan, metode pembayaran, dan kebijakan pembatalan akan diinformasikan secara transparan di aplikasi.
        Pengguna wajib membayar biaya layanan sesuai dengan rincian yang tercantum pada saat pemesanan.
    """.trimIndent(),

    "4. Hak dan Kewajiban Pengguna" to """
        Pengguna berhak mendapatkan informasi lengkap terkait layanan yang ditawarkan.
        Pengguna wajib memberikan akses lokasi untuk pelaksanaan layanan sesuai dengan jadwal yang telah disepakati.
        Pengguna tidak diperkenankan menggunakan layanan untuk tujuan ilegal atau melanggar hukum.
    """.trimIndent(),

    "5. Hak dan Kewajiban Agrojaya" to """
        Agrojaya berhak menolak atau membatalkan pesanan jika terdapat pelanggaran terhadap syarat dan ketentuan.
        Agrojaya bertanggung jawab menyediakan layanan sesuai dengan spesifikasi yang telah dijanjikan.
        Agrojaya tidak bertanggung jawab atas kerugian yang disebabkan oleh kesalahan pengguna, seperti kelalaian dalam perawatan pasca-instalasi.
    """.trimIndent(),

    "6. Hukum yang Berlaku" to """
        Syarat dan Ketentuan ini tunduk pada hukum yang berlaku di Republik Indonesia.
    """.trimIndent()
)