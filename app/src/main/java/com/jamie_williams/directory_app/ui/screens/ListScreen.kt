package com.jamie_williams.directory_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jamie_williams.directory_app.R
import com.jamie_williams.directory_app.models.EmployeeDataModel
import com.jamie_williams.directory_app.ui.custom.ExpandableEmployeeListAdapter
import com.jamie_williams.directory_app.ui.theme.Purple500
import com.jamie_williams.directory_app.utility.viewmodels.DirectoryState
import com.jamie_williams.directory_app.utility.viewmodels.EmployeeListViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    employeeViewModel: EmployeeListViewModel,
    refreshing: Boolean,
    onRefresh: () -> Unit,
) {
    val isRefreshingWorkaround = remember { mutableStateOf(refreshing) }
    LaunchedEffect(key1 = refreshing) {
        isRefreshingWorkaround.value = refreshing
    }
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isRefreshingWorkaround.value, onRefresh = onRefresh)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = when (employeeViewModel.state.collectAsState().value) {
            is DirectoryState.Empty -> {
                Alignment.Center
            }
            else -> {
                Alignment.TopCenter
            }
        }
    ) {
        when (employeeViewModel.state.collectAsState(DirectoryState.Loading).value) {
            is DirectoryState.Loading -> {
                ListScreen_Loading()
            }

            is DirectoryState.Error -> {
                if (!refreshing) {
                    ListScreen_Error(message = (employeeViewModel.state.collectAsState().value as DirectoryState.Error).message)
                }
            }

            is DirectoryState.Loaded -> {
                if (!refreshing) {
                    ListScreen_Loaded(employeeList = (employeeViewModel.state.collectAsState().value as DirectoryState.Loaded).data)
                }
            }

            is DirectoryState.Empty -> {
                if (!refreshing) {
                    ListScreen_Empty()
                }
            }
        }

        PullRefreshIndicator(refreshing = isRefreshingWorkaround.value, state = pullRefreshState)
    }
}


@Composable
fun ListScreen_Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ListScreen_Empty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_list),
            contentDescription = stringResource(
                R.string.empty_list_content_desc
            ),
            colorFilter = ColorFilter.tint(color = Purple500)
        )
        Text(
            text = stringResource(id = R.string.no_employees_error),
            modifier = Modifier.fillMaxSize(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListScreen_Error(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
    }
}

@Composable
fun ListScreen_Loaded(employeeList: List<EmployeeDataModel>) {
    ExpandableEmployeeListAdapter.EmployeeList(employeeList)
}