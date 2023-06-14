package com.jamie_williams.directory_app.models

import com.google.gson.annotations.SerializedName

/**
 * Data class for receiving a list of Employees datasets
 */
data class EmployeesApiResponse (
        @field:SerializedName("employees")
        val employees: List<EmployeeDataModel> = listOf()
)