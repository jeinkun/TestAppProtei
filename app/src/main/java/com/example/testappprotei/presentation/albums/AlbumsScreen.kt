package com.example.testappprotei.presentation.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testappprotei.presentation.navigation.PHOTOS_SCREEN_WITHOUT_ARG_ROUTE

@Composable
fun AlbumsScreen(vm: AlbumsViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(true) {
        vm.getAlbumsDb(navController.currentBackStackEntry?.arguments?.getString("userId")?.toInt())
    }
    val albumsUiState by vm.uiState.collectAsState()
    Box {
        LazyColumn {
            items(albumsUiState.albums.size)
            { album ->
                albumsUiState.albums[album]?.let { AlbumsCardView(it, vm, navController) }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumsCardView(album: Album, vm: AlbumsViewModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp)
            .shadow(elevation = 4.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray, shape = RoundedCornerShape(size = 8.dp))
            .combinedClickable(
                onLongClick = {
                    vm.deleteAlbumDb(album.id)
                },
                onClick = {
                    navController.navigate(PHOTOS_SCREEN_WITHOUT_ARG_ROUTE + album.id)
                })

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp)
        ) {
            val (text) = createRefs()
            Text(
                text = album.title ?: "",
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}