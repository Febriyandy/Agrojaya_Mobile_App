package com.febriandi.agrojaya.screens.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import com.febriandi.agrojaya.R

//Component button onboarding
@Composable
fun ButtonUI(
    text: String = "Ketuk Untuk Selanjutnya",
    backgroundColor: Color = colorResource(id = R.color.green_400),
    textColor: Color = colorResource(id = R.color.white),
    fontFamily: FontFamily = CustomFontFamily,
    fontSize: Int = 14,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(42.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
      {
        Text(
            text = text,
            color = textColor,
            style = TextStyle(
                fontFamily = fontFamily,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

    }
    Spacer(
        modifier = Modifier
            .size(20.dp)
    )
}

