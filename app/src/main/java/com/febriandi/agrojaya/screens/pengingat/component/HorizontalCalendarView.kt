package com.febriandi.agrojaya.screens.pengingat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class CalendarDate(
    val date: LocalDate,
    val isSelected: Boolean = false
)

//component horizontal calender
@Composable
fun HorizontalCalendarView(
    navController: NavController,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit
) {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    val scrollState = rememberLazyListState()

    val dates = remember(currentYearMonth) {
        val daysInMonth = currentYearMonth.lengthOfMonth()
        (1..daysInMonth).map { day ->
            CalendarDate(
                date = currentYearMonth.atDay(day),
                isSelected = selectedDate.dayOfMonth == day &&
                        selectedDate.month == currentYearMonth.month
            )
        }
    }

    LaunchedEffect(dates) {
        val today = LocalDate.now()
        if (currentYearMonth.month == today.month) {
            val centerPosition = (today.dayOfMonth - 1)
            val listSize = dates.size
            val halfScreenWidth = 7
            val scrollPosition = (centerPosition - halfScreenWidth / 2).coerceIn(0, listSize - 1)

            scrollState.scrollToItem(scrollPosition)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        val today = LocalDate.now()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${today.dayOfMonth}",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.green_500)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()), // Dynamic hari
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = CustomFontFamily,
                        style = androidx.compose.ui.text.TextStyle(
                            lineHeight = 24.sp
                        ),
                        color = colorResource(id = R.color.natural_400)
                    )
                    Text(
                        text = "${today.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${today.year}", // Dynamic bulan dan tahun
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = CustomFontFamily,
                        style = androidx.compose.ui.text.TextStyle(
                            lineHeight = 24.sp
                        ),
                        color = colorResource(id = R.color.natural_400)
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate("tambahPengingat")
                },
                modifier = Modifier
                    .height(35.dp)
                    .width(110.dp)
                    .clip(RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_100))
            ) {
                Text("Tambah", fontFamily = CustomFontFamily,
                    color = colorResource(id = R.color.green_500)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    currentYearMonth = currentYearMonth.minusMonths(1)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    tint = Color(0xFF4CAF50)
                )
            }

            Text(
                text = currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontFamily = CustomFontFamily
            )

            IconButton(
                onClick = {
                    currentYearMonth = currentYearMonth.plusMonths(1)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = Color(0xFF4CAF50)
                )
            }
        }


        LazyRow(
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(dates) { calendarDate ->
                DayItem(
                    date = calendarDate.date,
                    isSelected = calendarDate.date == selectedDate,
                    onDateClick = { date ->
                        selectedDate = date
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}
