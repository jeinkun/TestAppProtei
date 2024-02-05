package com.example.testappprotei.presentation.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testappprotei.R
import com.example.testappprotei.presentation.ErrorView
import com.example.testappprotei.presentation.ToolbarCommon
import com.example.testappprotei.presentation.navigation.PHOTOS_SCREEN_WITHOUT_ARG_ROUTE
import com.example.testappprotei.ui.theme.Grey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumsScreen(vm: AlbumsViewModel = viewModel(), navController: NavController) {
    val userId = navController.currentBackStackEntry?.arguments?.getString("userId")?.toInt()
    LaunchedEffect(true) {
        vm.getAlbumsDb(userId)
    }
    val albumsUiState by vm.uiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(albumsUiState.isLoading, { vm.getAlbums(userId) })

    Column {
        ToolbarCommon(stringResource(R.string.toolbar_title_albums))
        Box(
            Modifier
                .pullRefresh(pullRefreshState)
        ) {
            if (!albumsUiState.isError) {
                LazyColumn {
                    items(albumsUiState.albums.size)
                    { album ->
                        albumsUiState.albums[album]?.let { AlbumsCardView(it, vm, navController) }
                    }
                }
            } else {
                ErrorView()
            }

            PullRefreshIndicator(
                refreshing = albumsUiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
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
            .border(width = 1.dp, color = Grey, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
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
            val (text, icon) = createRefs()
            Text(
                text = album.title ?: "",
                fontSize = 20.sp,
                modifier = Modifier
                    .constrainAs(text) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            IconButton(
                onClick = {
                    vm.updateFavoriteAlbum(!album.favorite, album.id)
                },
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Image(
                    painter = painterResource(id = if (album.favorite) R.drawable.ic_favorite_orange else R.drawable.ic_favorite_gray),
                    contentDescription = "arrow",
                )
            }
        }
    }
}