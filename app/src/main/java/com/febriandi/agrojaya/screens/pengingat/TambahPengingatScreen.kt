package com.febriandi.agrojaya.screens.pengingat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.Pengingat
import com.febriandi.agrojaya.component.Header
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//Halaman tambah dan update pengingat
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPengingatScreen(
    navController: NavController,
    viewModel: PengingatViewModel = hiltViewModel(),
    pengingatId: Int? = null
) {
    val catatan = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedTime = remember { mutableStateOf("00:00 WIB") }
    val context = LocalContext.current

    val selectedPengingat by viewModel.selectedPengingat.collectAsState()


    LaunchedEffect(pengingatId) {
        pengingatId?.let {
            viewModel.getPengingatById(it)
        }
    }

    LaunchedEffect(selectedPengingat) {
        selectedPengingat?.let { pengingat ->
            catatan.value = pengingat.catatan
            selectedDate.value = pengingat.tanggal
            selectedTime.value = pengingat.jam
        }
    }

    val calendarState = rememberSheetState()
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            selectedDate.value = date
        }
    )

    val clockState = rememberSheetState()
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            selectedTime.value = String.format("%02d:%02d WIB", hours, minutes)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(
            navController,
            title = if (pengingatId == null) "Tambah Jadwal Aktivitas" else "Edit Jadwal Aktivitas"
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    textStyle = TextStyle(
                        fontFamily = CustomFontFamily,
                        color = colorResource(id = R.color.text_color)
                    ),
                    value = catatan.value,
                    onValueChange = { catatan.value = it },
                    label = { Text("Masukkan Catatan") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = colorResource(id = R.color.fill_form),
                        focusedContainerColor = colorResource(id = R.color.fill_form),
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = colorResource(id = R.color.green_400)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = colorResource(id = R.color.fill_form),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            fontFamily = CustomFontFamily,
                            text = selectedDate.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                color = colorResource(id = R.color.fill_form),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            fontFamily = CustomFontFamily,
                            text = selectedTime.value,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { calendarState.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_400))
                    ) {
                        Text(
                            "Pilih Tanggal",
                            fontFamily = CustomFontFamily
                        )
                    }

                    Button(
                        onClick = { clockState.show() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_400))
                    ) {
                        Text("Pilih Jam", fontFamily = CustomFontFamily)
                    }
                }

                Button(
                    onClick = {
                        if (catatan.value.isNotEmpty() &&
                            selectedTime.value != "00:00 WIB") {

                            val pengingat = if (pengingatId != null) {
                                // For editing existing reminder
                                Pengingat(
                                    id = pengingatId,
                                    jam = selectedTime.value,
                                    tanggal = selectedDate.value,
                                    catatan = catatan.value
                                )
                            } else {
                                // For adding new reminder
                                Pengingat(
                                    jam = selectedTime.value,
                                    tanggal = selectedDate.value,
                                    catatan = catatan.value
                                )
                            }

                            if (pengingatId != null) {
                                viewModel.perbaruidPengingat(pengingat)
                            } else {
                                viewModel.tambahPengingat(pengingat)
                            }

                            Toast.makeText(
                                context,
                                if (pengingatId != null) "Jadwal aktivitas berhasil diperbarui" else "Jadwal aktivitas berhasil disimpan",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "Mohon lengkapi semua data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_400))
                ) {
                    Text(if (pengingatId != null) "Perbarui" else "Simpan", fontFamily = CustomFontFamily)
                }
            }
        }
    }
}