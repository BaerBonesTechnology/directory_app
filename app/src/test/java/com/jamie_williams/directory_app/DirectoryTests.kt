package com.jamie_williams.directory_app

import com.jamie_williams.directory_app.repository.EmployeeRepository
import com.jamie_williams.directory_app.retrofit.RetrofitModule
import com.jamie_williams.directory_app.utility.Constants
import com.jamie_williams.directory_app.utility.Constants.MALFORMED_EMPLOYEE_ENDPOINT
import com.jamie_williams.directory_app.utility.viewmodels.DirectoryState
import com.jamie_williams.directory_app.utility.viewmodels.EmployeeListViewModel
import org.junit.Before
import org.junit.Test

class DirectoryTests {

    private lateinit var viewModel: EmployeeListViewModel

    /**
     * Uses the Retrofit API to create a request path in the form of a function
     */
    @Before
    fun setup(){
        val api = RetrofitModule.provideListApi()
        val repo = EmployeeRepository(api)
        viewModel = EmployeeListViewModel(repo)
    }

    @Test
    fun employeeApi_EnsureListHasNoNullNames(){
        viewModel.fetchList(MALFORMED_EMPLOYEE_ENDPOINT)
        when(viewModel.state.value){
            is DirectoryState.Loaded -> {
                val list = (viewModel.state.value as DirectoryState.Loaded).data
                for(employee in list){
                    assert(!employee.fullName.isNullOrEmpty() && !employee.id.isNullOrEmpty())
                }
            }
            is DirectoryState.Loading -> {
                // Do nothing to allow state to change to loaded
            }
            else -> {
                assert(false)
            }
        }
    }

    @Test
    fun employeeApi_BlankListMakeStateEmpty(){
        viewModel.fetchList(Constants.EMPTY_EMPLOYEE_ENDPOINT)
        when(viewModel.state.value){
            is DirectoryState.Empty -> {
                assert(true)
            }
            is DirectoryState.Loading -> {
                // Do nothing to allow state to change to empty
            }
            else -> {
                assert(false)
            }
        }
    }
}