package com.example.testappprotei.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testappprotei.presentation.albums.AlbumsScreen
import com.example.testappprotei.presentation.users.UsersScreen

const val USERS_SCREEN_ROUTE = "users"
const val ALBUMS_SCREEN_WITHOUT_ARG_ROUTE = "albums/"
const val ALBUMS_SCREEN_ROUTE = "albums/{userId}"

@Composable
fun BaseNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = USERS_SCREEN_ROUTE
    ) {
        composable(route = USERS_SCREEN_ROUTE) {
            UsersScreen(navController = navController)
        }
        composable(ALBUMS_SCREEN_ROUTE) {
            AlbumsScreen(navController = navController)
        }
    }
}