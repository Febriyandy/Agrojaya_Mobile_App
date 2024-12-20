package com.febriandi.agrojaya.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Component aktivitas home
@Composable
fun HomeScheduleSection(rootNavController: NavController) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            text = "Jadwal kegiatan",
            fontSize = 14.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.green_500)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.calender),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .alpha(0.8f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Atur Jadwal Aktivitas \nBertanimu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.text_color),
                    modifier = Modifier.padding(top = 10.dp)
                )

                Button(
                    onClick = {
                        rootNavController.navigate("jadwalAktivitas")
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green_400)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Atur Sekarang",
                        fontSize = 12.sp,
                        fontFamily = CustomFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}
