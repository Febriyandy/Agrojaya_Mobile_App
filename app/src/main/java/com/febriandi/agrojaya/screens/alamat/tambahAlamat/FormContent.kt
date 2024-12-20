package com.febriandi.agrojaya.screens.alamat.tambahAlamat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.Kabupaten
import com.febriandi.agrojaya.model.Kecamatan
import com.febriandi.agrojaya.model.Kelurahan
import com.febriandi.agrojaya.model.Provinsi
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Form Content
@Composable
fun FormContent(
    formState: AlamatFormState,
    onFormStateChange: (AlamatFormState) -> Unit,
    provinsiList: List<Provinsi>,
    kabupatenList: List<Kabupaten>,
    kecamatanList: List<Kecamatan>,
    kelurahanList: List<Kelurahan>,
    onProvinsiSelected: (Provinsi) -> Unit,
    onKabupatenSelected: (Kabupaten) -> Unit,
    onKecamatanSelected: (Kecamatan) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        // Common TextField properties
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.fill_form),
            unfocusedContainerColor = colorResource(id = R.color.fill_form),
            focusedBorderColor = colorResource(id = R.color.green_100),
            focusedTextColor = colorResource(id = R.color.text_color),
            unfocusedTextColor = colorResource(id = R.color.text_color),
            focusedLabelColor = colorResource(id = R.color.green_500),
            unfocusedLabelColor = colorResource(id = R.color.green_500),
            unfocusedBorderColor = colorResource(id = R.color.stroke_form),
            cursorColor = colorResource(id = R.color.text_color)
        )

        val textStyle = TextStyle(
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_color),
            fontFamily = CustomFontFamily
        )

        // Input Fields
        CustomTextField(
            value = formState.nama,
            onValueChange = { onFormStateChange(formState.copy(nama = it)) },
            label = "Nama Pembeli",
            colors = textFieldColors,
            textStyle = textStyle
        )

        CustomTextField(
            value = formState.noHp,
            onValueChange = { onFormStateChange(formState.copy(noHp = it)) },
            label = "Nomor HP",
            colors = textFieldColors,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        // Dropdowns
        LocationDropdown(
            items = provinsiList,
            selectedItem = formState.provinsi,
            onItemSelected = {
                onFormStateChange(formState.copy(provinsi = it))
                onProvinsiSelected(it)
            },
            label = "Provinsi",
            colors = textFieldColors
        )

        LocationDropdown(
            items = kabupatenList,
            selectedItem = formState.kabupaten,
            onItemSelected = {
                onFormStateChange(formState.copy(kabupaten = it))
                onKabupatenSelected(it)
            },
            label = "Kabupaten",
            colors = textFieldColors
        )

        LocationDropdown(
            items = kecamatanList,
            selectedItem = formState.kecamatan,
            onItemSelected = {
                onFormStateChange(formState.copy(kecamatan = it))
                onKecamatanSelected(it)
            },
            label = "Kecamatan",
            colors = textFieldColors
        )

        LocationDropdown(
            items = kelurahanList,
            selectedItem = formState.kelurahan,
            onItemSelected = {
                onFormStateChange(formState.copy(kelurahan = it))
            },
            label = "Kelurahan",
            colors = textFieldColors
        )

        CustomTextField(
            value = formState.alamat,
            onValueChange = { onFormStateChange(formState.copy(alamat = it)) },
            label = "Alamat Lengkap",
            colors = textFieldColors,
            textStyle = textStyle
        )

        CustomTextField(
            value = formState.catatan,
            onValueChange = { onFormStateChange(formState.copy(catatan = it)) },
            label = "Catatan",
            colors = textFieldColors,
            textStyle = textStyle
        )
    }
}
