package com.mirdar.catapiapp.ui.image_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mirdar.catapiapp.domain.model.CatImage

@Composable
fun ImageListScreen(
    imageListViewModel: ImageListViewModel = hiltViewModel(),
    onNavigateToImageDetail : (imageId : String) -> Unit
) {

    val state by imageListViewModel.state.collectAsStateWithLifecycle()
    val snackBarState = remember { SnackbarHostState() }
    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            snackBarState.showSnackbar(
                state.error,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(state.isLoading) {
        if (state.isLoading && state.imageList.isNotEmpty()) {
            snackBarState.showSnackbar(
                "Updating...",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackBarState)
    }) { padding ->
        when {
            state.imageList.isEmpty() && state.isLoading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            state.imageList.isNotEmpty() -> {
                val imageList = state.imageList
                LazyVerticalGrid(
                    GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(
                        imageList,
                        key = { it.id }
                    ) {
                        ImageItem(it) { id ->
                            onNavigateToImageDetail(id)
                        }
                    }
                }
            }
        }
    }

}

@Composable
@Preview
fun ImageItem(
    catImage: CatImage = CatImage(
        id = "123",
        url = "https://cdn2.thecatapi.com/images/2co.jpg",
        width = 1280,
        height = 720,
        true
    ),
    onItemClicked : (imageId : String) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(2.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable {
                onItemClicked(catImage.id)
            }
    ) {
        AsyncImage(
            model = catImage.url,
            contentDescription = "",
            modifier = Modifier.size(120.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = if (catImage.isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier.size(22.dp),
                tint = if (catImage.isFavourite) Color.Red else Color.Gray
            )
            Spacer(Modifier.width(8.dp))
            Text("Like", color = Color.Black)
        }
    }
}
