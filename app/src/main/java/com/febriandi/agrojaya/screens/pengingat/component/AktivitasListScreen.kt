package com.febriandi.agrojaya.screens.pengingat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.screens.pengingat.PengingatViewModel
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.time.LocalDate

//component aktivitas list
@Composable
fun AktivitasListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PengingatViewModel = hiltViewModel(),
    selectedDate: LocalDate = LocalDate.now(),
    onEditClicked: (Int) -> Unit,
    onDeleteClicked: (Int) -> Unit
) {

    viewModel.muatDaftarPengingat(selectedDate)

    val daftarPengingat by viewModel.daftarPengingat.collectAsState()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Waktu",
            fontFamily = CustomFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.text_color),
        )
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "Kegiatan",
            fontFamily = CustomFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.text_color),
        )
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(daftarPengingat) { aktivitas ->
            AktifitasItem(
                aktifitas = aktivitas,
                onEditClicked = {
                    onEditClicked(aktivitas.id)
                },
                onDeleteClicked = { id ->
                    viewModel.hapusPengingat(aktivitas)
                }
            )
        }
    }
}