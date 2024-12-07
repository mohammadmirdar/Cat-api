package com.mirdar.catapiapp.ui

enum class Screen {
    IMAGE_LIST,
    IMAGE_DETAIL
}
sealed class NavigationItem(val route: String) {
    data object ImageList : NavigationItem(Screen.IMAGE_LIST.name)
    data object ImageDetail : NavigationItem(Screen.IMAGE_DETAIL.name)
}