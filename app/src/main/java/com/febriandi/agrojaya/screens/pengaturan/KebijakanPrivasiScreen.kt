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

//Halaman Kebijakan privasi
@Composable
fun KebijakanPrivasiScreen(
    rootNavController: NavController
) {
    val scrollState = rememberScrollState()

    val termsText = buildAnnotatedString {
        append("Kami di Agrojaya berkomitmen untuk melindungi dan menghormati privasi Anda. Kebijakan Privasi ini menjelaskan bagaimana kami mengumpulkan, menggunakan, menyimpan, dan melindungi informasi pribadi Anda saat menggunakan aplikasi Agrojaya, yang menyediakan jasa pemasangan urban farming. \n\n")

        sections.forEach { (header, content) ->
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("$header\n")
            }
            append("$content\n\n")
        }

        append("\nDengan menggunakan aplikasi Agrojaya, Anda menyetujui pengumpulan dan penggunaan data sesuai dengan Kebijakan Privasi ini.")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            rootNavController,
            title = "Kebijakan Privasi"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Kebijakan Privasi Aplikasi Agrojaya",
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
    "1. Informasi yang Kami Kumpulkan" to """
        Informasi Pribadi: Nama, alamat, nomor telepon, alamat email, dan informasi lain yang Anda berikan saat mendaftar atau menggunakan layanan kami.
        Informasi Lokasi: Data lokasi geografis untuk memastikan akurasi dan kelancaran pelaksanaan layanan.
        Data Teknis: Informasi perangkat, alamat IP, jenis browser, dan data penggunaan aplikasi untuk tujuan analisis.
        Riwayat Transaksi: Informasi pemesanan, pembayaran, dan interaksi dengan layanan kami.
    """.trimIndent(),

    "2. Penggunaan Informasi" to """
        Memproses dan menyelesaikan pesanan Anda.
        Memberikan layanan yang sesuai dengan kebutuhan Anda, termasuk instalasi dan konsultasi urban farming.
        Menghubungi Anda untuk konfirmasi layanan, pembaruan, atau dukungan pelanggan.
        Meningkatkan performa aplikasi melalui analisis data dan umpan balik pengguna.
        Mengirimkan informasi promosi atau penawaran khusus (dengan persetujuan Anda).
    """.trimIndent(),

    "3. Keamanan Data" to """
        Kami menggunakan langkah-langkah teknis dan organisasi untuk melindungi informasi pribadi Anda dari akses tidak sah, kehilangan, atau penyalahgunaan. Meskipun demikian, tidak ada sistem keamanan yang sepenuhnya aman, sehingga kami tidak dapat menjamin keamanan absolut.
    """.trimIndent(),

    "4. Pembagian Informasi kepada Pihak Ketiga" to """
        Diperlukan untuk penyelesaian layanan, seperti bekerja sama dengan mitra instalasi.
        Diperlukan oleh hukum atau otoritas berwenang.
        Dengan persetujuan Anda untuk tujuan tertentu, seperti promosi bersama.
    """.trimIndent(),

    "5. Perubahan Kebijakan Privasi" to """
        Kami dapat memperbarui Kebijakan Privasi ini sewaktu-waktu untuk mencerminkan perubahan dalam layanan atau kebijakan hukum. Perubahan akan diberitahukan melalui aplikasi atau email Anda.
    """.trimIndent()
)