package com.febriandi.agrojaya.screens.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.screens.pengingat.PengingatViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

//Component aktivitas home
@Composable
fun HomeAktivitasComponent(
    viewModel: PengingatViewModel,
    modifier: Modifier = Modifier,
    rootNavController: NavController
) {
    val pengingatSaatIni by viewModel.pengingatSaatIni.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.mulaiPemantauanPengingat()
    }

    Text(
        modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp),
        text = "Jadwal kegiatan",
        fontSize = 14.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.green_500)
    )

    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.green_400)),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            val today = LocalDate.now()
            Column (
            ){
                Text(
                    text = "${today.dayOfMonth}",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = CustomFontFamily,
                    style = androidx.compose.ui.text.TextStyle(
                        lineHeight = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = colorResource(id = R.color.green_400)
                )
                Text(
                    text = today.dayOfWeek.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = CustomFontFamily,
                    style = androidx.compose.ui.text.TextStyle(
                        lineHeight = 24.sp
                    ),
                    color = colorResource(id = R.color.natural_400)
                )
                Text(
                    text = "${
                        today.month.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        )
                    } ${today.year}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = CustomFontFamily,
                    style = androidx.compose.ui.text.TextStyle(
                        lineHeight = 24.sp
                    ),
                    color = colorResource(id = R.color.natural_400)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            rootNavController.navigate("jadwalAktivitas")
                        },
                        modifier = Modifier
                            .height(35.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_400))
                    ) {
                        Text(
                            "Lihat Semua",
                            fontFamily = CustomFontFamily,
                            color = colorResource(id = R.color.white)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = pengingatSaatIni?.let {
                        "Aktivitas Anda Selanjutnya : \nPukul ${it.jam}, ${it.catatan}"
                    } ?: "Tidak ada aktivitas yang dijadwalkan",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Normal,
                    fontFamily = CustomFontFamily,
                    style = androidx.compose.ui.text.TextStyle(
                        lineHeight = 24.sp
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.text_color)
                )
            }
        }
    }
}