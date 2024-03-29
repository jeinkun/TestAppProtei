package com.example.testappprotei.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testappprotei.R
import com.example.testappprotei.presentation.ToolbarCommon
import com.example.testappprotei.presentation.commonViews.CardView
import com.example.testappprotei.presentation.commonViews.ErrorView
import com.example.testappprotei.presentation.navigation.ALBUMS_SCREEN_WITHOUT_ARG_ROUTE

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersScreen(vm: UsersViewModel = viewModel(), navController: NavController) {
    val usersUiState by vm.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(usersUiState.isLoading, { vm.getUsers() })
    Column {
        ToolbarCommon(stringResource(R.string.toolbar_title_users))
        Box(
            Modifier
                .pullRefresh(pullRefreshState)
        ) {
            if (!usersUiState.isError) {
                LazyColumn(
                    modifier = Modifier.background(
                        Color.White,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                ) {
                    items(usersUiState.users.size)
                    { user ->
                        usersUiState.users[user]?.let { UserCardView(it, navController, vm) }
                    }
                }
            } else {
                ErrorView()
            }
            PullRefreshIndicator(
                refreshing = usersUiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun UserCardView(user: User, navController: NavController, vm: UsersViewModel) {
    CardView(
        onLongClick = {
            vm.deleteUserDb(user.id)
        },
        onClick = {
            navController.navigate(ALBUMS_SCREEN_WITHOUT_ARG_ROUTE + user.id)
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = user.name ?: "",
                    fontSize = 20.sp
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "arrow",
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                )

            }
        })
}