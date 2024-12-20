package com.febriandi.agrojaya.screens.transaksi.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Order status item
@Composable
fun OrderStatusItem(
    status: String,
    currentStatus: String
) {
    fun getStatusOrder(status: String): Int = when (status) {
        "Menunggu Pembayaran" -> 1
        "Pesanan Dibuat" -> 2
        "Teknisi Menuju Lokasi Anda" -> 3
        "Proses Pemasangan" -> 4
        "Pesanan Selesai" -> 5
        else -> 0
    }

    fun getCurrentStatusOrder(status: String): Int = when (status) {
        "Menunggu Pembayaran" -> 1
        "Pesanan Dibuat" -> 2
        "Teknisi Menuju Lokasi Anda" -> 3
        "Proses Pemasangan" -> 4
        "Pesanan Selesai" -> 5
        else -> 0
    }

    val statusOrder = getStatusOrder(status)
    val currentStatusOrder = getCurrentStatusOrder(currentStatus)

    val isCompleted = statusOrder < currentStatusOrder
    val isActive = statusOrder == currentStatusOrder

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_radio),
            contentDescription = "Status",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(
                if (isCompleted || isActive) colorResource(id = R.color.green_400) else Color.Gray
            )
        )


        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = status,
            fontSize = 14.sp,
            fontFamily = CustomFontFamily,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isActive || isCompleted) colorResource(id = R.color.green_500)
            else colorResource(id = R.color.text_color)
        )
    }

    if (status != "Pesanan Selesai") {
        Box(
            modifier = Modifier
                .padding(start = 11.dp)
                .width(2.dp)
                .height(20.dp)
                .background(
                    if (isCompleted || isActive) colorResource(id = R.color.green_500)
                    else Color.Gray
                )
        )
    }
}
