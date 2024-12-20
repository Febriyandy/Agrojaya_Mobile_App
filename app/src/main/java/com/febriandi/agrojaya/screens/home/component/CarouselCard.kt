package com.febriandi.agrojaya.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.screens.Paket.PaketItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import com.febriandi.agrojaya.R
import kotlin.math.absoluteValue

//Component Carousel di home
@Composable
fun PaketCarousel(
    paketList: List<PaketResponse>,
    onItemClicked: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { paketList.size }
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % paketList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue


            val scale = calculateScale(pageOffset)

            PaketItem(
                paket = paketList[page],
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .scale(scale)
                    .graphicsLayer {
                        alpha = 1f - pageOffset.coerceIn(0f, 1f) * 0.3f
                    },
                onItemClicked = onItemClicked
            )
        }


        PageIndicator(
            pageCount = paketList.size,
            currentPage = pagerState.currentPage
        )
    }
}


private fun calculateScale(pageOffset: Float): Float {
    return 1f - (pageOffset * 0.2f).coerceIn(0f, 0.2f)
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            val color = if (currentPage == index)
                colorResource(id = R.color.green_400)
            else
                colorResource(id = R.color.natural_300)

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}