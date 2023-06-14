package com.jamie_williams.directory_app.ui.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jamie_williams.directory_app.R
import com.jamie_williams.directory_app.models.EmployeeDataModel
import com.jamie_williams.directory_app.utility.EmployeeType
import com.skydoves.landscapist.glide.GlideImage


object ExpandableEmployeeListAdapter {
    @Composable
    fun ExpandableEmployeeListAdapter(employee: EmployeeDataModel) {
        val expanded = remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row {
                    GlideImage(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clip(CircleShape),
                        imageModel = employee.photoLarge ?: "", // Add image url from user
                        contentScale = ContentScale.Crop,
                        placeHolder = painterResource(id = R.drawable.placeholder_image),
                        error = painterResource(id = R.drawable.placeholder_image),
                        requestBuilder = Glide
                            .with(LocalContext.current)
                            .asDrawable()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Row {
                        Column {
                            Text(
                                text = employee.fullName!!,
                                fontWeight = FontWeight.Bold
                            ) //replace with employee name
                            Text(
                                text = String.format(
                                    stringResource(id = R.string.team_label),
                                    employee.team ?: stringResource(
                                        R.string.team_not_added_error
                                    ),
                                    employee.employeeType?.title ?: EmployeeType.CONTRACTOR.title
                                ),
                                fontSize = 12.sp,
                                color = Color.Gray
                            ) //replace with employee team
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(.6f),
                                text = stringResource(id = R.string.employee_id_label, employee.id),
                                fontSize = 8.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.End
                            ) //replace with employee id
                        }
                    }
                }
                AnimatedVisibility(visible = expanded.value) {
                    Column(Modifier.padding(10.dp)) {
                        Row {
                            if (!employee.phoneNumber.isNullOrEmpty()) {
                                Row {

                                    Icon(
                                        Icons.Filled.Phone,
                                        contentDescription = stringResource(id = R.string.phone_label),
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        tint = Color.Gray
                                    )
                                    Text(
                                        text = employee.phoneNumber,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    ) //replace with employee phone number
                                }
                            }
                            if (!employee.phoneNumber.isNullOrEmpty() && !employee.emailAddress.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            if (!employee.phoneNumber.isNullOrEmpty()) {
                                Row {
                                    Icon(
                                        Icons.Filled.Email,
                                        contentDescription = stringResource(id = R.string.email_label),
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        tint = Color.Gray
                                    )
                                    Text(
                                        text = employee.emailAddress ?: "",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    ) //replace with employee email address
                                }
                            }
                        }
                        if (!employee.photoSmall.isNullOrEmpty()) {
                            Text(
                                text = stringResource(id = R.string.gallery_label),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                            )
                            GlideImage(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp),
                                imageModel = employee.photoSmall, // Add image url from user
                                contentScale = ContentScale.Crop,
                                placeHolder = painterResource(id = R.drawable.placeholder_image),
                                error = painterResource(id = R.drawable.placeholder_image),
                                requestBuilder = Glide
                                    .with(LocalContext.current)
                                    .asDrawable()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                            )
                        }
                        if (!employee.biography.isNullOrEmpty()) {

                            Text(
                                text = String.format(
                                    stringResource(id = R.string.about_label),
                                    employee.fullName!!.split(" ")[0]
                                ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                            )
                            Text(
                                text = employee.biography,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun EmployeeList(list: List<EmployeeDataModel>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list.size) { index ->
                ExpandableEmployeeListAdapter(employee = list[index])
            }
        }

    }
}