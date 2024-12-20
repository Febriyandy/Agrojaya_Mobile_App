package com.febriandi.agrojaya.screens.alamat.tambahAlamat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Submit Button
@Composable
fun SubmitButton(
    isSubmitting: Boolean,
    formState: AlamatFormState,
    onSubmit: () -> Unit,
    onValidationError: () -> Unit
) {
    Button(
        onClick = {
            if (formState.isValid()) {
                onSubmit()
            } else {
                onValidationError()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.green_400),
        ),
        shape = RoundedCornerShape(8.dp),
        enabled = !isSubmitting
    ) {
        Text(
            text = "Simpan",
            color = Color.White,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}
