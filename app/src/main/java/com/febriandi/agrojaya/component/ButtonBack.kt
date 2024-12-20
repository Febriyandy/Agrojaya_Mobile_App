package com.febriandi.agrojaya.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.febriandi.agrojaya.R

//Component Button Back
@Composable
fun ButtonBack(
    backgroundColor: Color = colorResource(id = R.color.green_400),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(40.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_back), 
            contentDescription = "Back Icon",
            modifier = Modifier.size(30.dp)
        )
    }
}

