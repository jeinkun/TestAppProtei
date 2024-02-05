package com.example.testappprotei.presentation

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testappprotei.ui.theme.Orange

@Composable
fun ToolbarCommon(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = Orange,
        contentColor = Color.White,
        elevation = 2.dp
    )
}