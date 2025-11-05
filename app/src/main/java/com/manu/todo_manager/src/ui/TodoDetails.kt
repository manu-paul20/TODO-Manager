package com.manu.todo_manager.src.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manu.todo_manager.R
import com.manu.todo_manager.src.database.Todo
import com.manu.todo_manager.src.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTodoDetails(todo: Todo,navController: NavController){
    val topAppBarColor = listOf(
        colorResource(R.color.SoftBlue),
        colorResource(R.color.CoolGray)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable{navController.navigate(
                                Routes.Home
                            )}
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "TODO Details",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
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
    ) {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
           Text(
               text = todo.todoName,
               fontSize = 50.sp,
               fontWeight = FontWeight.ExtraBold
           )
            Spacer(Modifier.height(10.dp))
            if(!todo.startDate.equals("NA") && !todo.endDate.equals("NA")){
                Text(
                    text = "${todo.startDate} to ${todo.endDate}",
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = todo.todoDescription,
                fontSize = 30.sp
            )
        }
    }
}