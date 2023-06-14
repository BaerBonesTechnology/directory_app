package com.jamie_williams.directory_app.utility.viewmodels

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jamie_williams.directory_app.R
import com.jamie_williams.directory_app.models.EmployeeDataModel
import com.jamie_williams.directory_app.models.Resource
import com.jamie_williams.directory_app.repository.MainRepository
import com.jamie_williams.directory_app.utility.Constants.EMPLOYEE_ENDPOINT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EMPLOYEE MANAGER"

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DirectoryState>(DirectoryState.Loading)
    private val _isRefreshing = MutableStateFlow(false)

    val state: StateFlow<DirectoryState>
        get() = _state
    val isRefreshing: Boolean
        get() = _isRefreshing.value

    init {
        _state.value = DirectoryState.Loading
    }

    /**
     * Function to populate the list of employees, with a default query of the employee endpoint.
     * For testing purposes, the query can be changed to the empty or malformed employee endpoints
     */
    fun fetchList(query: String = EMPLOYEE_ENDPOINT) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = DirectoryState.Loading
            when (val response = repository.getEmployees(query)) {
                is Resource.Success -> {
                    val list = mutableListOf<EmployeeDataModel>()
                    for (employee in response.data?.employees ?: listOf()) {
                        if (employee.fullName.isNullOrEmpty() || employee.id.isNullOrEmpty()) {
                            Log.d(TAG, "Employee missing vital data: $employee")
                            continue
                        }
                        list.add(employee)
                    }
                    if (list.isEmpty()) {
                        _state.value = DirectoryState.Empty
                        return@launch
                    }
                    _state.value = DirectoryState.Loaded(list)
                }

                is Resource.Error -> {
                    _state.value = DirectoryState.Error(
                        response.message ?: Resources.getSystem().getString(
                            R.string.unknown_error_text
                        )
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.emit(DirectoryState.Loading)
            when (val response = repository.getEmployees()) {
                is Resource.Success -> {
                    val list = response.data?.employees ?: listOf()
                    if (list.isEmpty()) {
                        _state.emit(DirectoryState.Empty)
                        return@launch
                    }
                    _state.emit(DirectoryState.Loaded(list))
                }

                is Resource.Error -> {
                    _state.emit(
                        DirectoryState.Error(
                            response.message ?: Resources.getSystem().getString(
                                R.string.unknown_error_text
                            )
                        )
                    )
                }
            }
            _isRefreshing.emit(false)
        }
    }
}

sealed class DirectoryState {
    object Empty : DirectoryState()
    object Loading : DirectoryState()
    class Loaded(val data: List<EmployeeDataModel>) : DirectoryState()
    class Error(val message: String) : DirectoryState()
}