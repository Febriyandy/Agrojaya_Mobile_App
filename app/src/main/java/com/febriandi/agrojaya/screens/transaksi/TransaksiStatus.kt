package com.febriandi.agrojaya.screens.transaksi

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.model.PaymentStatus
import com.febriandi.agrojaya.screens.transaksi.viewmodel.DetailTransaksiViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.utils.Resource
import java.text.SimpleDateFormat
import java.util.Locale

//Halaman Status Transaksi
@Composable
fun TransaksiStatus(
    navController: NavController,
    rootNavController: NavController,
    orderId: String?,
    viewModel: DetailTransaksiViewModel = hiltViewModel()
) {
    val paymentState = viewModel.paymentState.collectAsState()

    LaunchedEffect(orderId) {
        orderId?.let { viewModel.loadPaymentStatus(it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = paymentState.value) {
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
                TransaksiStatusContent(
                    payment = state.data,
                    onItemClicked = {
                        navController.navigate("detailTransaksi/$orderId")
                    },
                    onItemBack = {
                        rootNavController.navigate("mainScreen")
                    }
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
                        text = "Silahkan Pilih Metode Pembayaran",
                        color = Color.Red,
                        fontFamily = CustomFontFamily
                    )
                    Button(
                        onClick = { orderId?.let { viewModel.loadPaymentStatus(it) } },
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
private fun TransaksiStatusContent(
    payment: PaymentStatus,
    onItemBack: () -> Unit,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("id", "ID"))
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH.mm 'WIB'", Locale("id", "ID"))
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            "-"
        }
    }

    fun formatCurrency(amount: String): String {
        return try {
            val number = amount.toDouble()
            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            formatter.format(number).replace("Rp", "Rp ")
        } catch (e: Exception) {
            "-"
        }
    }

    val (imageResource, statusText, descriptionText) = when (payment.transaction_status) {
        "settlement" -> Triple(
            R.drawable.status_sukses,
            "Pembayaran Berhasil",
            "Transaksi anda berhasil. Kami akan segera menuju lokasi anda, Terima Kasih"
        )
        "pending" -> Triple(
            R.drawable.status_pending,
            "Menunggu Pembayaran",
            "Silahkan melakukan pembayaran dengan metode yang sudah anda pilih"
        )
        "cancel" -> Triple(
            R.drawable.status_gagal,
            "Pembayaran Gagal",
            "Transaksi anda gagal, silahkan melakukan transaksi ulang"
        )
        else -> Triple(
            R.drawable.status_gagal,
            "Status Tidak Diketahui",
            "Terjadi kesalahan pada transaksi"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                alignment = Alignment.Center
            )
            Text(
                text = statusText,
                fontSize = 20.sp,
                style = TextStyle(
                    lineHeight = 30.sp
                ),
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_color),
            )
            Text(
                text = formatCurrency(payment.gross_amount),
                fontSize = 16.sp,
                style = TextStyle(
                    lineHeight = 10.sp
                ),
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_color),
            )

            Text(
                text = formatDate(payment.settlement_time),
                fontSize = 14.sp,
                style = TextStyle(
                    textAlign = TextAlign.Center
                ),
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Text(
                text = descriptionText,
                fontSize = 14.sp,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                ),
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
        Button(
            onClick = { onItemBack ()
               },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(34.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.green_400),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = "Kembali Ke Beranda",
                fontWeight = FontWeight.Medium,
                fontFamily = CustomFontFamily,
                fontSize = 14.sp,
                color = colorResource(id = R.color.green_400)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        ButtonComponent(
            text = "Lihat Detail Transaksi",
            onClick = { onItemClicked(payment.order_id)}
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
