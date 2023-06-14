package com.jamie_williams.directory_app.models.api

import com.jamie_williams.directory_app.models.EmployeesApiResponse
import com.jamie_williams.directory_app.utility.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {
    /**
     * Uses the Retrofit API to create a request path in the form of a function
     */
    @GET("{path}")
    suspend fun getList(@Path("path") query: String = Constants.EMPLOYEE_ENDPOINT) : Response<EmployeesApiResponse>
}