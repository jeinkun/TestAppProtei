package com.example.testappprotei.presentation.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testappprotei.presentation.navigation.ALBUMS_SCREEN_WITHOUT_ARG_ROUTE

import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.testappprotei.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersScreen(vm: UsersViewModel = viewModel(), navController: NavController) {
    val usersUiState by vm.uiState.collectAsState()
//    val pullRefreshState = rememberPullRefreshState(usersUiState.isLoading, { vm.getUsersDb() })
    Box {
//        Modifier.pullRefresh(pullRefreshState)
        LazyColumn {
            items(usersUiState.users.size)
            { user ->
                usersUiState.users[user]?.let { UserCardView(it, navController, vm) }
            }
        }
//        PullRefreshIndicator(
//            refreshing = usersUiState.isLoading,
//            state = pullRefreshState,
//            modifier = Modifier.align(Alignment.TopCenter),
//        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserCardView(user: User, navController: NavController, vm: UsersViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp)
            .shadow(elevation = 4.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray, shape = RoundedCornerShape(size = 8.dp))
            .combinedClickable(
                onLongClick = {
                    vm.deleteUserDb(user.id)
                },
                onClick = {
                    navController.navigate(ALBUMS_SCREEN_WITHOUT_ARG_ROUTE + user.id)
                })

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp)
        ) {
            val (text, icon) = createRefs()
            Text(
                text = user.name ?: "",
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "arrow",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

        }
    }
}