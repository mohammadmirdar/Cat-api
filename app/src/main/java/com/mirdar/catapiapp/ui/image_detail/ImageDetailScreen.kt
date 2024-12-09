package com.mirdar.catapiapp.ui.image_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mirdar.catapiapp.domain.model.Breed
import com.mirdar.catapiapp.domain.model.ImageDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    imageDetailViewModel: ImageDetailViewModel = hiltViewModel(),
    onBackClicked : () -> Unit
) {
    val state by imageDetailViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                { Text("Cat Detail") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        Modifier.clickable { onBackClicked()}.padding(end = 10.dp))
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Action */ }) {
                Icon(Icons.Default.Share, contentDescription = "Share")
            }
        },
        content = { padding ->
            when {
                state.isLoading && state.imageDetail == ImageDetail.EMPTY -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                state.imageDetail != ImageDetail.EMPTY -> {
                    val imageDetail = state.imageDetail
                    val imageBreed =
                        if (imageDetail.breeds.isNotEmpty()) imageDetail.breeds[0] else Breed()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(Color(0xFFF0F4F8)) // Light pastel background
                    ) {
                        // Cat Image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .shadow(4.dp)
                        ) {
                            AsyncImage(
                                model = imageDetail.url,
                                contentDescription = "Cat Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        // Cat Details
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = imageBreed.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextWithIcon(
                                    "Like",
                                    "",
                                    if (!imageDetail.isFavourite) Icons.Default.FavoriteBorder else Icons.Default.Favorite
                                ) {
                                    imageDetailViewModel.handleIntent(
                                        ImageDetailIntent.MakeImageFavourite(
                                            imageDetail.id,
                                            !imageDetail.isFavourite
                                        )
                                    )
                                }
                                TextWithIcon(
                                    "Temperament",
                                    imageBreed.temperament,
                                    Icons.Default.Face
                                )
                                TextWithIcon("Origin", imageBreed.origin, Icons.Default.Place)
                                TextWithIcon(
                                    "Lifespan",
                                    "${imageBreed.lifeSpan} years",
                                    Icons.Default.DateRange
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = { /* Open Wikipedia */ }) {
                                    Text("Wikipedia", color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TextWithIcon(label: String, value: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF555555)
        )
    }
}