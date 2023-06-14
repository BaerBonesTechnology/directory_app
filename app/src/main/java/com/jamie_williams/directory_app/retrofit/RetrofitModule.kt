package com.jamie_williams.directory_app.retrofit

import com.jamie_williams.directory_app.models.api.EmployeeApi
import com.jamie_williams.directory_app.repository.EmployeeRepository
import com.jamie_williams.directory_app.repository.MainRepository
import com.jamie_williams.directory_app.utility.Constants.API_ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    // Sets up an instance of retrofit for api calls to get employee data
    @Singleton
    @Provides
    fun provideListApi():EmployeeApi = Retrofit.Builder()
        .baseUrl(API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployeeApi::class.java)

    @Singleton
    @Provides
    fun provideRepo(employees: EmployeeApi) : MainRepository = EmployeeRepository(employees)
}