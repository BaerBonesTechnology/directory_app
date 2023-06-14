package com.jamie_williams.directory_app.repository

import com.jamie_williams.directory_app.models.EmployeesApiResponse
import com.jamie_williams.directory_app.models.Resource
import com.jamie_williams.directory_app.utility.Constants

/**
 * Interface for employee repository
 */
interface MainRepository{
    suspend fun getEmployees(query: String = Constants.EMPLOYEE_ENDPOINT): Resource<EmployeesApiResponse>
}