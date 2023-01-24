package com.example.favourite_list_screen.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data_source.model.persistence.Image
import com.example.favourite_list_screen.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteCatListScreen(listImages: LazyPagingItems<Image>, onFavouriteTap: (Image) -> Unit, onDownload: (Image) -> Unit) {
    val refreshing by remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(refreshing, listImages::refresh)

    LazyVerticalGrid(
        modifier = Modifier
            .pullRefresh(state)
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp
            ),
        columns = GridCells.Fixed(3)
    ) {
        when (val state = listImages.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
        when (val state = listImages.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
        items(
            listImages.itemCount
        ) {
            Image(
                image = listImages[it],
                onDownload = onDownload,
                onFavouriteTap = { listImages[it]?.let { image -> onFavouriteTap.invoke(image) }
            })
        }
        when (val state = listImages.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }
            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Image(image: Image?, onFavouriteTap: () -> Unit, onDownload: (Image) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        DownloadAlertDialog(showDialog, onDownload, image)
    }
    Box(
        modifier = Modifier.size(100.dp, 132.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image?.url)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_cat),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.fillMaxSize().combinedClickable(
                onClick = {},
                onLongClick = {
                    showDialog.value = true
                }
            )
        )
        Icon(
            modifier = Modifier
                .clickable {
                    onFavouriteTap.invoke()
                }
                .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.ic_star_full),
            contentDescription = null
        )
    }
}

@Composable
private fun DownloadAlertDialog(isShown: MutableState<Boolean>, onDownload: (Image) -> Unit, image: Image?) {
    AlertDialog(onDismissRequest = { isShown.value = false },
        confirmButton = {
            TextButton(onClick = {
                image?.let { onDownload.invoke(it) }
                isShown.value = false
            })
            { Text(text = "OK") }
        },
        dismissButton = {
            TextButton(onClick = {
                isShown.value = false
            })
            { Text(text = "Cancel") }
        })
}

private fun LazyGridScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

private fun LazyGridScope.Error(
    message: String
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error
        )
    }
}