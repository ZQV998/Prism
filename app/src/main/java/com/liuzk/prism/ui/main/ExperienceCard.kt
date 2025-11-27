package com.liuzk.prism.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.liuzk.prism.R
import com.liuzk.prism.data.model.Experience

@Composable
fun ExperienceCard(
    experience: Experience,
    onLikeClick: (Experience) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(experience.coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                contentDescription = experience.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(experience.coverUrl.toAspectRatio())
                    .clip(RoundedCornerShape(8.dp)),
                onSuccess = { Log.d("Coil", "Image loaded successfully: ${experience.coverUrl}") },
                onError = { Log.e("Coil", "Error loading image: ${experience.coverUrl}", it.result.throwable) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = experience.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(experience.avatarUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = experience.username,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onLikeClick(experience) }) {
                    Icon(
                        imageVector = if (experience.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (experience.isLiked) Color.Red else Color.Gray
                    )
                }
                Text(
                    text = experience.likes.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

private fun String?.toAspectRatio(): Float {
    if (this.isNullOrBlank()) return 1f

    return try {
        val urlParts = this.split("/")
        if (urlParts.size < 2) return 1f

        val width = urlParts[urlParts.size - 2].toFloat()
        val height = urlParts[urlParts.size - 1].toFloat()

        if (width > 0 && height > 0) width / height else 1f
    } catch (e: Exception) {
        1f
    }
}

@Preview
@Composable
fun ExperienceCardPreview() {
    val experience = Experience(
        id = "1",
        title = "This is a long title that should wrap to two lines",
        coverUrl = "https://picsum.photos/400/600",
        avatarUrl = "https://picsum.photos/100/100",
        username = "John Doe",
        likes = 123,
        isLiked = true
    )
    ExperienceCard(experience = experience, onLikeClick = {})
}
