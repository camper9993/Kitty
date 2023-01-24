package com.example.kitty.presentation.ui

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.example.cat_list_screen.presentation.ui.CatListScreen
import com.example.kitty.R
import com.example.data_source.model.persistence.Image
import com.example.favourite_list_screen.presentation.ui.FavouriteCatListScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {
    class CatList(listImages: LazyPagingItems<Image>, onFavouriteTap: (Image) -> Unit) : TabItem(R.drawable.ic_cat, "Лента", { CatListScreen(listImages, onFavouriteTap) })
    class Favourite(listImages: LazyPagingItems<Image>, onFavouriteTap: (Image) -> Unit, onDownload: (Image) -> Unit) : TabItem(R.drawable.ic_star_full, "Избранное", { FavouriteCatListScreen(listImages, onFavouriteTap, onDownload) })
}