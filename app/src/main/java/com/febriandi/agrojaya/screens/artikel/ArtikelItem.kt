package com.febriandi.agrojaya.screens.artikel


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Artikel Item
@Composable
fun ArtikelItem(
    artikel: ArtikelResponse,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable { onItemClicked(artikel.id) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_50))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = artikel.photo,
                    contentDescription = "Article thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    onLoading = { isLoading = true },
                    onSuccess = { isLoading = false }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorResource(id = R.color.green_400)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = artikel.judul,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.text_color),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    fontFamily = CustomFontFamily
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = artikel.isi,
                    fontSize = 10.sp,
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.natural_400),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        lineHeight = 16.sp,
                        textAlign = Justify
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Column {
                        Text(
                            text = artikel.penulis,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.text_color),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = CustomFontFamily
                        )
                    }

                }
            }
        }
    }

}


