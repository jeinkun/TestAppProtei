package com.example.testappprotei.presentation.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.testappprotei.R
import com.example.testappprotei.presentation.ErrorView
import com.example.testappprotei.presentation.ToolbarCommon
import com.example.testappprotei.ui.theme.Grey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotosScreen(vm: PhotosViewModel = viewModel(), navController: NavController) {
    val albumId = navController.currentBackStackEntry?.arguments?.getString("albumId")?.toInt()
    LaunchedEffect(true) {
        vm.getPhotosDb(albumId)
    }
    val photosUiState by vm.uiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(photosUiState.isLoading, { vm.getPhotos(albumId) })

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoCardView(photo: Photo, vm: PhotosViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp)
            .shadow(elevation = 4.dp)
            .border(width = 1.dp, color = Grey, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
            .combinedClickable(
                onLongClick = {
                    vm.deletePhotoDb(photo.id)
                },
                onClick = {})

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (image, text) = createRefs()
            AsyncImage(
                model = photo.url,
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            )
            Text(
                text = photo.title ?: "",
                fontSize = 20.sp,
                modifier = Modifier
                    .constrainAs(text) {
                        start.linkTo(parent.start)
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(20.dp, 8.dp, 20.dp, 8.dp)
            )
        }
    }
}