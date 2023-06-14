package com.jamie_williams.directory_app.repository

import com.jamie_williams.directory_app.models.EmployeesApiResponse
import com.jamie_williams.directory_app.models.Resource
import com.jamie_williams.directory_app.models.api.EmployeeApi
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val employeesApi: EmployeeApi
) : MainRepository {
    override suspend fun getEmployees(query: String): Resource<EmployeesApiResponse> {
        return try {
            // sets response to retrieved data from employeesApi and if successful sends the data to EmployeesApiResponse to be parsed into a list of Employee objects
        val response = employeesApi.getList(query)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }
}