package com.manu.todo_manager.src.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.manu.todo_manager.R
import com.manu.todo_manager.src.database.Todo
import com.manu.todo_manager.src.database.ViewModels.EditScreenViewModel
import com.manu.todo_manager.src.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodo(
    todo: Todo,
    navController: NavController,
    viewModel: EditScreenViewModel = viewModel()
) {
    DisplayBackHandler()

    val topAppBarColor = listOf(
        colorResource(R.color.SoftBlue),
        colorResource(R.color.CoolGray)
    )
    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()
    val startDate = formatDate(startDatePickerState.selectedDateMillis)
    val endDate = formatDate(endDatePickerState.selectedDateMillis)
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var todoName by remember { mutableStateOf(todo.todoName) }
    var todoDescription by remember { mutableStateOf(todo.todoDescription) }
   val todoStartDate = if(startDate.isBlank()) todo.startDate else startDate
    val todoEndDate = if(endDate.isBlank()) todo.endDate else endDate
    val todoNameErrorMessage = "Todo name cannot be empty"
    val todoDescriptionErrorMessage = "Todo description cannot be empty"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.fillMaxWidth(0.9f)) {
                            Row {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Routes.Home)
                                    }
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Edit TODO",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                if(todoName.isNotBlank()&&todoDescription.isNotBlank()){
                                    viewModel.updateTodo(
                                        Todo(
                                            Id = todo.Id,
                                            todoName = todoName,
                                            todoDescription = todoDescription,
                                            startDate = todoStartDate,
                                            endDate = todoEndDate
                                        )
                                    )
                                    Toast.makeText(context, "Todo Updated", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.navigate(Routes.Home)
                                }
                            }
                        )
                    }
                },
                modifier = Modifier.background(
                    brush = Brush.linearGradient(topAppBarColor)
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = todoName,
                onValueChange = { todoName = it },
                isError = todoName.isBlank(),
                label = { Text("TODO Name", color = Color.Blue) },
                supportingText = {Text(
                    text = if(todoName.isBlank()) todoNameErrorMessage else "",
                    color = Color.Red
                )},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = todoDescription,
                isError = todoDescription.isBlank(),
                onValueChange = { todoDescription = it },
                label = { Text("TODO Description", color = Color.Blue) },
                supportingText = {Text(
                    text = if(todoDescription.isBlank()) todoDescriptionErrorMessage else "",
                    color = Color.Red
                )},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = if (startDate.isBlank()) todo.startDate else todoStartDate,
                onValueChange = {showStartDatePicker=true},
                label = { Text("Start Date", color = Color.Blue) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        Modifier.clickable { showStartDatePicker = true }
                    )
                }
            )
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = if (endDate.isBlank()) todo.endDate else todoEndDate,
                onValueChange = {showEndDatePicker = true},
                label = { Text("End Date", color = Color.Blue) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        Modifier.clickable { showEndDatePicker = true }
                    )
                }
            )
        }
    }
    if (showStartDatePicker) {
        CustomDatePickerDialog(
            datePickerState = startDatePickerState,
            onDismissRequest = { showStartDatePicker = false }
        )
    }
    if (showEndDatePicker) {
        CustomDatePickerDialog(
            datePickerState = endDatePickerState,
            onDismissRequest = { showEndDatePicker = false }
        )
    }
}


@Composable
fun DisplayBackHandler(){
    val currentTime = System.currentTimeMillis()
    var lastBackPress by remember { mutableStateOf(0L) }
    val backPressInterval = 2000L
    val context = LocalContext.current
    BackHandler {

        if(currentTime - lastBackPress < backPressInterval){
            (context as? Activity)?.finish()
        }else{
            Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
            lastBackPress = currentTime
        }
    }
}