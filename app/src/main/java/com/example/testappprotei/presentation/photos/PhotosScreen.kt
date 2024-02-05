package com.example.testappprotei.presentation.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun PhotosScreen(vm: PhotosViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(true) {
        vm.getPhotosDb(
            navController.currentBackStackEntry?.arguments?.getString("albumId")?.toInt()
        )
    }
    val photosUiState by vm.uiState.collectAsState()

    Box {
        LazyColumn {
            items(photosUiState.photos.size)
            { photo ->
                photosUiState.photos[photo]?.let { PhotoCardView(it, vm) }
            }
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
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray, shape = RoundedCornerShape(size = 8.dp))
            .combinedClickable(
                onLongClick = {
                    vm.deletePhotoDb(photo.id)
                },
                onClick = {})

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp)
        ) {
            val (image, text) = createRefs()
            Image(
                painter = rememberAsyncImagePainter(photo.url),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
            )
            Text(
                text = photo.title ?: "",
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}