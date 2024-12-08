package com.mirdar.catapiapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mirdar.catapiapp.ui.image_list.ImageListScreen
import kotlinx.serialization.Serializable

@Serializable
data object ImageListItem
@Serializable
data class ImageDetailItem(val imageId : String)

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ImageListItem
    ) {
        composable<ImageListItem> {
            ImageListScreen { id ->
                navController.navigate(route = ImageDetailItem(imageId = id))
            }
        }

        composable<ImageDetailItem>{

        }
    }
}