package com.manu.todo_manager.src.ui


import android.app.DatePickerDialog
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.manu.todo_manager.R
import com.manu.todo_manager.src.database.CompletedTodo
import com.manu.todo_manager.src.database.Todo
import com.manu.todo_manager.src.database.ViewModels.TodoViewModel
import com.manu.todo_manager.src.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// main screen
fun Home(viewModel: TodoViewModel = viewModel(), navController: NavController) {

    DisplayBackHandler()

    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
// for bottom sheet
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var isSheetOpen by remember { mutableStateOf(false) }

// top app bar color
    val topAppBarColor = listOf(
        colorResource(R.color.SoftBlue),
        colorResource(R.color.CoolGray)
    )

    // todo list
    val todoList by viewModel.todoList.collectAsStateWithLifecycle(initialValue = emptyList())

    LaunchedEffect(Unit) { }
    val completedTodo by viewModel.completedTodo.collectAsStateWithLifecycle(initialValue = emptyList())

    // manipulating todo  list arrangement
    val customArrangement = if (todoList.isEmpty()) Arrangement.Center else Arrangement.Top

    //for add todo dialog
    var isAddTodoDialogOpened by remember { mutableStateOf(false) }

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "TODO Manager",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    ),

                    modifier = Modifier.background(brush = Brush.linearGradient(topAppBarColor))
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                brush = Brush.linearGradient(topAppBarColor),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        onClick = { isAddTodoDialogOpened = true },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                    ) {
                        Text("Add TODO")
                    }
                    Spacer(Modifier.width(10.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text("${completedTodo.size}", fontSize = 20.sp)
                                }
                            },
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.linearGradient(topAppBarColor),
                                        shape = RoundedCornerShape(50.dp)
                                    ),
                                onClick = {
                                    isSheetOpen = true
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                            ) {
                                Text("Completed TODOs'")
                            }
                        }
                    }
                }

            }

        ) { innerPadding ->
            // list of all Todo
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = customArrangement,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (todoList.size >= 1) {
                    items(
                        todoList,
                        key = { it.Id }
                    ) { todo ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(90.dp)
                                .clickable {
                                    navController.navigate(
                                        Routes.TodoDetails(
                                            todoName = todo.todoName,
                                            todoDestination = todo.todoDescription,
                                            startDate = todo.startDate,
                                            endDate = todo.endDate
                                        )
                                    )
                                }
                                .animateItem(
                                    fadeOutSpec = tween(200),
                                    fadeInSpec = tween(800)
                                ),
                            elevation = CardDefaults.elevatedCardElevation(20.dp),
                            shape = CardDefaults.elevatedShape
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Column(
                                    Modifier.fillMaxWidth(0.8f)
                                ) {
                                    Text(
                                        text = todo.todoName,
                                        fontSize = 24.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = todo.todoDescription,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 20.sp,
                                        color = Color.Gray
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            navController.navigate(
                                                Routes.EditTodo(
                                                    id = todo.Id,
                                                    todoName = todo.todoName,
                                                    todoDestination = todo.todoDescription,
                                                    startDate = todo.startDate,
                                                    endDate = todo.endDate
                                                )
                                            )
                                        }
                                    )
                                )
                                Spacer(Modifier.width(10.dp))
                                HomeScreenCheckBox {
                                    viewModel.deleteTodo(todo.Id)
                                    viewModel.addCompletedTodo(
                                        CompletedTodo(
                                            id = todo.Id,
                                            todoName = todo.todoName,
                                            todoDescription = todo.todoDescription
                                        )
                                    )
                                }


                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = "There is no Todo",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    // bottom sheet
    if (isSheetOpen) {
        BottomSheet(
            completedTodo = completedTodo,
            onDismissRequest = { isSheetOpen = false },
            sheetState = modalBottomSheetState,
            viewModel = viewModel
        )
    }
    // add todo dialog
    if (isAddTodoDialogOpened) {
        AddTODO(
            todoList = todoList,
            onDismissRequest = { isAddTodoDialogOpened = false },
            viewModel = viewModel
        )
    }

}

// composable for bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    completedTodo: List<CompletedTodo>,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    viewModel: TodoViewModel
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            items(
                completedTodo,
                key = { it.id }
            ) { todo ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(70.dp)
                        .animateItem(
                            fadeInSpec = tween(800),
                            fadeOutSpec = tween(300),
                        ),
                    elevation = CardDefaults.elevatedCardElevation(20.dp),
                    shape = CardDefaults.elevatedShape
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Column(
                            Modifier.fillMaxWidth(0.9f)
                        ) {
                            Text(
                                text = todo.todoName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                            Text(
                                text = todo.todoDescription,
                                maxLines = 1,
                                fontSize = 16.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.deleteCompletedTodo(todo.id)
                            }
                        )

                    }
                }
            }
        }
    }
}


// function for add todo dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTODO(todoList: List<Todo>, onDismissRequest: () -> Unit, viewModel: TodoViewModel) {
    var todoName by remember { mutableStateOf("") }
    var todoDescription by remember { mutableStateOf("") }
    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()
    var startDate = formatDate(startDatePickerState.selectedDateMillis)
    var endDate = formatDate(endDatePickerState.selectedDateMillis)
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var nameErrorMessage by remember { mutableStateOf("") }
    var isDescriptionError by remember { mutableStateOf(false) }
    var descriptionErrorMessage by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card {
                Column(Modifier.padding(30.dp)) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Transparent
                            ) {
                                Text("*", color = Color.Red, fontSize = 20.sp)
                            }
                        }
                    ) {
                        OutlinedTextField(
                            value = todoName,
                            onValueChange = {
                                if(it.length<=30) {todoName = it}
                                isNameError = false
                                nameErrorMessage = ""
                            },
                            supportingText = {
                                Text(
                                    text = nameErrorMessage,
                                    color = Color.Red
                                )
                            },
                            trailingIcon = {Text("${todoName.length}/30")},
                            isError = isNameError,
                            label = { Text(" TODO name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    BadgedBox(
                        badge = {
                            Badge(containerColor = Color.Transparent) {
                                Text("*", color = Color.Red, fontSize = 20.sp)
                            }
                        }
                    ) {
                        OutlinedTextField(
                            value = todoDescription,
                            onValueChange = {
                                if(todoDescription.length<=100){todoDescription = it}
                                isDescriptionError = false
                                descriptionErrorMessage = ""
                            },
                            trailingIcon = {Text("${todoDescription.length}/100")},
                            supportingText = {
                                Text(
                                    text = descriptionErrorMessage,
                                    color = Color.Red
                                )
                            },
                            label = { Text(" TODO description") },
                            singleLine = false,
                            isError = isDescriptionError,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = {},
                        label = { Text("Start date") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                null,
                                Modifier.clickable { showStartDatePicker = true }
                            )
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = {},
                        label = { Text("End date") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                null,
                                Modifier.clickable { showEndDatePicker = true }
                            )
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (todoName.isNotBlank() && todoDescription.isNotBlank()) {
                                    viewModel.insertTodo(
                                        Todo(
                                            todoName = todoName,
                                            todoDescription = todoDescription,
                                            startDate = startDate.ifBlank { "NA" },
                                            endDate = endDate.ifBlank { "NA" }
                                        )
                                    )
                                    onDismissRequest()
                                } else if (todoName.isBlank()) {
                                    isNameError = true
                                    nameErrorMessage = "Todo name is required"
                                } else {
                                    isDescriptionError = true
                                    descriptionErrorMessage = "Todo description is required"
                                }

                            }
                        ) {
                            Text("Add")
                        }
                        Spacer(Modifier.width(10.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = onDismissRequest
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }

    // manipulation date pickers'
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

fun formatDate(milis: Long?): String {
    return milis?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(milis)
    } ?: ""
}

// function for date picker
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(datePickerState: DatePickerState, onDismissRequest: () -> Unit) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

// function for check box
@Composable
fun HomeScreenCheckBox(onClick: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            isChecked = it
            onClick()
        }
    )
}
