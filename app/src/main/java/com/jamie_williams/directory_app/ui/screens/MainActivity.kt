package com.jamie_williams.directory_app.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jamie_williams.directory_app.ui.theme.Directory_appTheme
import com.jamie_williams.directory_app.utility.viewmodels.EmployeeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val employeeViewModel: EmployeeListViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val pullRefreshState = rememberPullRefreshState(
                employeeViewModel.isRefreshing,
                { employeeViewModel.refresh() })
            Directory_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    ListScreen(
                        employeeViewModel = employeeViewModel,
                        refreshing = employeeViewModel.isRefreshing,
                        onRefresh = { employeeViewModel.refresh() })
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeeViewModel.fetchList()
            }
        }
    }
}
