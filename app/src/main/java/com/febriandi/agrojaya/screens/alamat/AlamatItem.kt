package com.febriandi.agrojaya.screens.alamat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.component.ButtonComponent
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.model.AlamatResponse

//Alamat Item
@Composable
fun AlamatItem(
    alamat: AlamatResponse,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit = {}
) {
    Card(
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.green_400)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.fill_form))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = alamat.nama,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = CustomFontFamily,
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.height(4.dp))
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
                style = TextStyle(
                    lineHeight = 17.sp
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily,
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "[${alamat.catatan}]",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = CustomFontFamily,
                style = TextStyle(
                    lineHeight = 17.sp
                ),
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = Modifier.height(14.dp))
            ButtonComponent (
                text = "Ubah Alamat",
                onClick = {
                    onItemClicked(alamat.id)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlamatItemPreview() {
    val sampleAlamat = AlamatResponse(
        id = 1,
        uid = "wjwjwjwjjw",
        nama = "Adjie Cahya Ramadhan",
        noHp = "082991829282",
        alamatLengkap = "Griya Exotica Cinangka Blok M no.3",
        kelurahan = "Cinangka",
        kecamatan = "Sawangan",
        kabupaten = "Kota Depok",
        provinsi = "Jawa Barat",
        catatan = "Lurus sampai ujung gang, baru belok kanan"
    )

    MaterialTheme {
        AlamatItem(alamat = sampleAlamat)
    }
}