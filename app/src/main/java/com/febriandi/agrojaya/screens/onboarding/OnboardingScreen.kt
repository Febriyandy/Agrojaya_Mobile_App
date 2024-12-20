package com.febriandi.agrojaya.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

//Halaman onboarding
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages: List<OnboardingModel> = listOf(
        OnboardingModel.FirstPage,
        OnboardingModel.SecondPage,
        OnboardingModel.ThirdPage
    )

    val pagerState: PagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }


    val scope = rememberCoroutineScope()

    val buttonText: State<String> = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                pages.size - 1 -> "Mulai" // Last page
                else -> "Ketuk Untuk Selanjutnya"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(5.dp))


        HorizontalPager(state = pagerState) { index ->
            OnboardingGraphUI(onboardingModel = pages[index])
        }


        IndicatorUI(
            pageSize = pages.size,
            currentPage = pagerState.currentPage
        )


        ButtonUI(
            text = buttonText.value,
            onClick = {
                scope.launch {
                    if (pagerState.currentPage < pages.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onFinished()
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen {
    }
}
