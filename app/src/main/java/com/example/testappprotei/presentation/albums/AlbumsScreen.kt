package com.example.testappprotei.presentation.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.testappprotei.presentation.navigation.PHOTOS_SCREEN_WITHOUT_ARG_ROUTE

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumsScreen(vm: AlbumsViewModel = viewModel(), navController: NavController) {
    val userId = navController.currentBackStackEntry?.arguments?.getString("userId")?.toInt()
    LaunchedEffect(true) {
        vm.getAlbums(userId)
    }
    val albumsUiState by vm.uiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(albumsUiState.isLoading, { vm.getAlbumsUpdate(userId) })

    Column {
        ToolbarCommon(stringResource(R.string.toolbar_title_albums))
        Box(
            Modifier
                .pullRefresh(pullRefreshState)
        ) {
            if (!albumsUiState.isError) {
                LazyColumn {

                    itemsIndexed(albumsUiState.albums.filterNotNull()) { index, _ ->
                        albumsUiState.albums[index]?.let { AlbumsCardView(it, vm, navController) }
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

@Composable
fun AlbumsCardView(album: Album, vm: AlbumsViewModel, navController: NavController) {

    CardView(onClick = { navController.navigate(PHOTOS_SCREEN_WITHOUT_ARG_ROUTE + album.id) },
        onLongClick = { vm.deleteAlbumDb(album.id) }, content = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    text = album.title ?: "",
                    fontSize = 20.sp,
                )

                IconButton(
                    onClick = {
                        vm.updateFavoriteAlbum(!album.favorite, album.id)
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = if (album.favorite) R.drawable.ic_favorite_orange else R.drawable.ic_favorite_gray),
                        contentDescription = "arrow",
                    )
                }
            }
        })
}