package com.febriandi.agrojaya.screens.pengingat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.Pengingat
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.time.LocalDate

//Aktivitas Item
@Composable
fun AktifitasItem(
    aktifitas: Pengingat,
    modifier: Modifier = Modifier,
    onEditClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = aktifitas.jam,
                fontFamily = CustomFontFamily,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text_color)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Card (modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.fill_form))
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(100.dp),
                    text = aktifitas.catatan,
                    fontFamily = CustomFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    color = colorResource(id = R.color.text_color),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = { onEditClicked(aktifitas.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green_400)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(30.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_edit),
                            contentDescription = "Back Icon",
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { onDeleteClicked(aktifitas.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(5.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(30.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_delete),
                            contentDescription = "Back Icon",
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AktivitasItemPreview(){
    val sampleAktifitas = Pengingat(
        id = 1,
        jam = "14:30",
        tanggal = LocalDate.now(),
        catatan = "Melakukan pembersihan kebun "
    )

    MaterialTheme {
        AktifitasItem(
            aktifitas = sampleAktifitas,
            onEditClicked = {},
            onDeleteClicked = {}
        )
    }
}