package com.mirdar.catapiapp.ui.image_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mirdar.catapiapp.R
import com.mirdar.catapiapp.domain.model.CatImage

@Composable
fun ImageListScreen(
    navController: NavController,
    imageListViewModel: ImageListViewModel = hiltViewModel()
) {
    val state by imageListViewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> CircularProgressIndicator()
        state.error.isNotEmpty() -> {
            Column {
                Text("Error: ${state.error}")
                Button(onClick = { imageListViewModel.handleIntent(ImageListIntent.LoadImageList) }) {
                    Text("Retry")
                }
            }
        }

        state.imageList.isNotEmpty() -> {
            val imageList = state.imageList
            LazyColumn {
                items(
                    count = imageList.size,
                    key = { imageList[it].id }
                ) {
                    ImageItem(imageList[it])
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
        url = "test",
        width = 1280,
        height = 720,
        true
    )
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(140.dp)
            .background(color = colorResource(R.color.white))
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_favorite),
                contentDescription = "",
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(color = if (catImage.isFavourite) Color.Red else Color.Gray)
            )
            Spacer(Modifier.width(8.dp))
            Text("Like", color = Color.Black)
        }
    }
}
