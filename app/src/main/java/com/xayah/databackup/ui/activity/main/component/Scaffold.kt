package com.xayah.databackup.ui.activity.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.xayah.databackup.R
import com.xayah.databackup.ui.activity.main.page.guide.GuideViewModel
import com.xayah.databackup.ui.component.BottomSpacer
import com.xayah.databackup.ui.component.SlotScope
import com.xayah.databackup.ui.component.TopSpacer
import com.xayah.databackup.ui.component.paddingHorizontal
import com.xayah.databackup.ui.token.CommonTokens

@ExperimentalMaterial3Api
@Composable
fun GuideScaffold(
    scaffoldNavController: NavHostController,
    navController: NavHostController,
    viewModel: GuideViewModel,
    content: @Composable () -> Unit
) {
    val uiState = viewModel.uiState.value
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { uiState.onFabClick(scaffoldNavController, navController) }) {
                Icon(uiState.fabIcon, null)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.paddingHorizontal(CommonTokens.PaddingMedium)) {
            TopSpacer(innerPadding = innerPadding)
            TopSpacer(innerPadding = innerPadding)

            GuideTopBar(title = uiState.title, icon = uiState.icon)
            content()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SlotScope.MainScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTopBar(title = stringResource(id = R.string.app_name))
        },
        bottomBar = {
            MainBottomBar()
        }
    ) { innerPadding ->
        Column {
            TopSpacer(innerPadding = innerPadding)

            content()

            BottomSpacer(innerPadding = innerPadding)
        }
    }
}
