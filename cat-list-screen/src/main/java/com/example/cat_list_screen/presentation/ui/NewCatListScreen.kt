package com.example.cat_list_screen.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.cat_list_screen.R
import com.example.data_source.model.persistence.Image

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatListScreen(listImages: LazyPagingItems<Image>, onFavouriteTap: (Image) -> Unit) {

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
        //Вот здесь надо бы удалять из списка котиков элемент, который выбрали изрбранным,
        // но в Paging 3 это делается через костыли, поэтому не стал этого делать.
        // Вообще как я понял, лучше писать свою реализацию пагинации
        items(
            listImages.itemCount
        ) {
            Image(image = listImages[it]) { image ->
                onFavouriteTap.invoke(image)
            }
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

@Composable
private fun Image(image: Image?, onFavouriteTap: (Image) -> Unit) {
    var isFavourChanged by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.size(100.dp, 132.dp)) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(image?.url)
            .crossfade(true)
            .build(),
            placeholder = painterResource(R.drawable.ic_cat),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.fillMaxSize())
        Icon(
            modifier = Modifier
                .clickable {
                    if (!isFavourChanged) {
                        image?.let {
                            onFavouriteTap.invoke(image)
                            isFavourChanged = !isFavourChanged
                        }
                    }
                }
                .align(Alignment.TopEnd),
            painter = painterResource(id = if (isFavourChanged) {
                R.drawable.ic_star_full
            } else {
                R.drawable.ic_star_outline
            }),
            contentDescription = null)
    }

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
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}