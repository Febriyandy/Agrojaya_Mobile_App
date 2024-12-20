package com.febriandi.agrojaya.screens.pengaturan

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Halaman Kontak Kami
@Composable
fun KontakKamiScreen(
    rootNavController: NavController,
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Header(
            rootNavController,
            title = "Kontak Kami"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ContactBox(
                context = context,
                icon = painterResource(id = R.drawable.call),
                title = "Telepon",
                value = "085162598308",
                onClick = {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:085162598308")
                    context.startActivity(dialIntent)
                }
            )

            ContactBox(
                context = context,
                icon = painterResource(id = R.drawable.icon_wa),
                title = "WhatsApp",
                value = "085162598308 (Chat Only)",
                onClick = {
                    val message = "Halo Agrojaya"
                    val waIntent = Intent(Intent.ACTION_VIEW)
                    waIntent.data = Uri.parse("https://api.whatsapp.com/send?phone=6285162598308&text=$message")
                    context.startActivity(waIntent)
                }
            )

            ContactBox(
                context = context,
                icon = painterResource(id = R.drawable.icon_email),
                title = "Email",
                value = "csagrojaya@agrojaya.com",
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO)
                    emailIntent.data = Uri.parse("mailto:csagrojaya@agrojaya.com")
                    context.startActivity(emailIntent)
                }
            )
        }
    }
}

@Composable
fun ContactBox(
    context: Context,
    icon: Painter,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.green_100)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.text_color)
                    )
                    Text(
                        text = value,
                        fontSize = 12.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.natural_400)
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_back2),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}