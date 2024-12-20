package com.febriandi.agrojaya.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.febriandi.agrojaya.R

//Component indicator onboarding
@Composable
fun IndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = colorResource(id = R.color.green_400),
    unselectedColor: Color = colorResource(id = R.color.natural_200)
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageSize) {

            Box(
                modifier = Modifier
                    .height(7.dp)
                    .width(width = if (it == currentPage) 20.dp else 7.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = if (it == currentPage) selectedColor else unselectedColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview1() {
    IndicatorUI(pageSize = 3, currentPage = 0)
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview2() {
    IndicatorUI(pageSize = 3, currentPage = 1)
}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview3() {
    IndicatorUI(pageSize = 3, currentPage = 2)
}
