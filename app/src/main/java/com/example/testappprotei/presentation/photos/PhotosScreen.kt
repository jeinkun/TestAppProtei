package com.example.testappprotei.presentation.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.testappprotei.R
import com.example.testappprotei.presentation.ToolbarCommon
import com.example.testappprotei.presentation.commonViews.CardView
import com.example.testappprotei.presentation.commonViews.ErrorView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotosScreen(vm: PhotosViewModel = viewModel(), navController: NavController) {
    val albumId = navController.currentBackStackEntry?.arguments?.getString("albumId")?.toInt()
    LaunchedEffect(true) {
        vm.getPhotos(albumId)
    }
    val photosUiState by vm.uiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(photosUiState.isLoading, { vm.getPhotosUpdate(albumId) })

    Column {
        ToolbarCommon(stringResource(R.string.toolbar_title_photos))
        Box(
            Modifier
                .pullRefresh(pullRefreshState)
        ) {
            if (!photosUiState.isError) {
                LazyColumn {
                    items(photosUiState.photos.size)
                    { photo ->
                        photosUiState.photos[photo]?.let { PhotoCardView(it, vm) }
                    }
                }
            } else {
                ErrorView()
            }
            PullRefreshIndicator(
                refreshing = photosUiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun PhotoCardView(photo: Photo, vm: PhotosViewModel) {
    CardView(
        onLongClick = {
            vm.deletePhotoDb(photo.id)
        },
        onClick = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 8.dp),
            ) {
                AsyncImage(
                    model = photo.url,
                    contentDescription = "Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = photo.title ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(20.dp, 8.dp, 20.dp, 8.dp)
                )
            }
        })
}