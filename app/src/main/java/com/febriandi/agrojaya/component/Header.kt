package com.febriandi.agrojaya.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.ui.theme.CustomFontFamily

//Component Header
@Composable
fun Header(
    navController: NavController,
    title: String
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        ButtonBack { navController.popBackStack() }
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = title,
            fontSize = 14.sp,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.text_color)
        )
    }
}