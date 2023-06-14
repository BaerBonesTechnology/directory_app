package com.jamie_williams.directory_app.models


import com.google.gson.annotations.SerializedName
import com.jamie_williams.directory_app.utility.EmployeeType

/**
 * Employee Datasets
 */
data class EmployeeDataModel(
    @field:SerializedName("uuid")
    private val _uuid: String?,
    @field:SerializedName("full_name")
    val fullName: String?,
    @field:SerializedName("phone_number")
    val phoneNumber: String?,
    @field:SerializedName("email_address")
    val emailAddress: String?,
    @field:SerializedName("biography")
    val biography: String?,
    @field:SerializedName("photo_url_small")
    val photoSmall: String?,
    @field:SerializedName("photo_url_large")
    val photoLarge: String?,
    @field:SerializedName("team")
    val team: String?,
    @field:SerializedName("employee_type")
    val employeeType: EmployeeType?
){
    val id: String
        get(){
            if(_uuid.isNullOrEmpty()) return ""
            return String.format("****%s",_uuid.substring(_uuid.length-4, _uuid.length))
        }
}