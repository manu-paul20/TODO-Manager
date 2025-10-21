package com.manu.todo_manager.src.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manu.todo_manager.R
import com.manu.todo_manager.src.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun StartScreen(navController: NavController){
    val fullScreenColor = listOf(
        colorResource(R.color.SoftBlue),
        colorResource(R.color.CoolGray)
    )
    var isAnimating by remember { mutableStateOf(false) }
    val animateLogo = animateDpAsState(
        targetValue = if(isAnimating) 0.dp else  600.dp,
        animationSpec = tween(1000)
    )
    LaunchedEffect(Unit) {
        isAnimating = true

        delay(2000)

        navController.navigate(Routes.Home)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(fullScreenColor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            Image(
                painter = painterResource(R.drawable.applogo),
                modifier = Modifier.offset(y = animateLogo.value),
                contentDescription = null
            )
        Spacer(Modifier.height(10.dp))
        AnimatedVisibility(
            visible = isAnimating,
            enter = fadeIn(tween(3000))
        ) {
            Text(
                text = "Todo Manager",
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}