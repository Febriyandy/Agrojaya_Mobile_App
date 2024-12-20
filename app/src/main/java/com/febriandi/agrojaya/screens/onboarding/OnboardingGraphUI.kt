package com.febriandi.agrojaya.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.febriandi.agrojaya.ui.theme.CustomFontFamily
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.febriandi.agrojaya.R

//component onboarding
@Composable
fun OnboardingGraphUI(onboardingModel: OnboardingModel) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier.size(50.dp,)
        )

        Text(
            text = onboardingModel.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.text_color)
        )

        Spacer(
            modifier = Modifier
                .size(60.dp)
        )

        Image(
            painter = painterResource(id = onboardingModel.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp, 0.dp),
            alignment = Alignment.Center
            )

        Spacer(
            modifier = Modifier
                .size(100.dp)
        )

        Text(
            text = onboardingModel.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontFamily = CustomFontFamily,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.text_color)
        )

        Spacer(
            modifier = Modifier
                .size(10.dp)
                .fillMaxWidth()
        )
    }



}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview1() {
    OnboardingGraphUI(OnboardingModel.FirstPage)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview2() {
    OnboardingGraphUI(OnboardingModel.SecondPage)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview3() {
    OnboardingGraphUI(OnboardingModel.ThirdPage)
}
