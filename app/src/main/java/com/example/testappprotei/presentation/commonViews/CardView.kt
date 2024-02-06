package com.example.testappprotei.presentation.commonViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.testappprotei.ui.theme.Grey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardView(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp)
            .shadow(elevation = 4.dp)
            .border(width = 1.dp, color = Grey, shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colors.onBackground, shape = RoundedCornerShape(8.dp))
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = onClick
            )

    ) {
        content()
    }
}