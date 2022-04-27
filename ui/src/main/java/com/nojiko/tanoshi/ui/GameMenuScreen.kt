package com.nojiko.tanoshi.ui

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

@Composable
@ExperimentalPagerApi
fun GameMenu() {
    val pagerState = rememberPagerState()
    val pages = remember {
        listOf("Personnages", "Mangas", "Culture Jap")
    }
    HorizontalPager(count = 3) { page ->
        Text(text = "Page: $page")
    }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun SimpleComposable() {
    Text("Hello World")
}

@Preview
@Composable
@ExperimentalPagerApi
fun ComposablePreview() {
    SimpleComposable()
}