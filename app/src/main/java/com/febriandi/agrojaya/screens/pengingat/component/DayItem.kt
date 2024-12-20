package com.febriandi.agrojaya.screens.pengingat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

//Item calender hari
@Composable
fun DayItem(
    date: LocalDate,
    isSelected: Boolean,
    onDateClick: (LocalDate) -> Unit
) {
    val isToday = date == LocalDate.now()
    val backgroundColor = when {
        isSelected -> colorResource(id = R.color.green_400)
        isToday -> colorResource(id = R.color.green_100)
        else -> Color.Transparent
    }
    val textColor = when {
        isSelected -> Color.White
        else -> Color.Black
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onDateClick(date) }
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).first().toString(),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = CustomFontFamily
        )


        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
