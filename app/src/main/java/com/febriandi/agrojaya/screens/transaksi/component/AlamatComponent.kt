package com.febriandi.agrojaya.screens.transaksi.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//component alamat transaksi
@Composable
fun AlamatCard(
    alamat: AlamatResponse,
    isSelected: Boolean,
    onSelect: (AlamatResponse) -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp)
            .clickable { onSelect(alamat) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) colorResource(id = R.color.green_50) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = alamat.nama,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.text_color)
                )
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelect(alamat) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(id = R.color.green_400),
                        uncheckedColor = Color.Gray
                    )
                )
            }
            Text(
                text = alamat.noHp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily,
                style = TextStyle(
                    lineHeight = 17.sp
                ),
                color = colorResource(id = R.color.text_color)
            )

            Text(
                text = buildString {
                    append(alamat.alamatLengkap)
                    append(", ")
                    append("Kelurahan ${alamat.kelurahan}")
                    append(", ")
                    append("Kecamatan ${alamat.kecamatan}")
                    append(", ")
                    append(alamat.kabupaten)
                    append(", ")
                    append("Provinsi ${alamat.provinsi}")
                },
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    lineHeight = 17.sp
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily,
                color = colorResource(id = R.color.text_color)
            )
        }
    }
}

@Composable
fun AlamatList(
    alamats: List<AlamatResponse>,
    selectedAlamat: AlamatResponse?,
    onAlamatSelect: (AlamatResponse) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(alamats) { alamat ->
            AlamatCard(
                alamat = alamat,
                isSelected = selectedAlamat?.id == alamat.id,
                onSelect = onAlamatSelect
            )
        }
    }
}


