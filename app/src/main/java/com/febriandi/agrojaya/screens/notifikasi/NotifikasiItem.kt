package com.febriandi.agrojaya.screens.notifikasi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.NotificationModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Notifikasi item
@Composable
fun NotifikasiItem(
    notifikasi: NotificationModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 3.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.green_50)
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = notifikasi.title ?: "Tidak ada judul",
                fontSize = 12.sp,
                color = colorResource(id = R.color.text_color),
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFontFamily
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notifikasi.body ?: "Tidak ada deskripsi",
                fontSize = 12.sp,
                color = colorResource(id = R.color.natural_500),
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatWaktu(notifikasi.waktu ?: System.currentTimeMillis()),
                fontSize = 12.sp,
                color = colorResource(id = R.color.text_color),
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily
            )
        }
    }
}


fun formatWaktu(waktu: Long): String {
    return try {
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        formatter.format(Date(waktu))
    } catch (e: Exception) {
        "Waktu tidak tersedia"
    }
}