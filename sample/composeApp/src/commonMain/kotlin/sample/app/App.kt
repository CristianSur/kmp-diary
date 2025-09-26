// DiaryPage.kt
package sample.app

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DiaryEntry(val id: Int, val text: String)


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    var entry by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf(listOf<DiaryEntry>()) }
    var showAll by remember { mutableStateOf(false) }
    var nextId by remember { mutableStateOf(0) }

    val visibleEntries = remember(entries, showAll) {
        if (showAll) entries else entries.takeLast(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7D5))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("📔 My Diary", fontSize = 22.sp, color = Color(0xFF1F2A44))
            Button(onClick = { /* navigate to profile */ }) {
                Text("←")
            }
        }

        Spacer(Modifier.height(12.dp))

        // Entries
        if (visibleEntries.isEmpty()) {
            Text(
                "No diary entries yet. Write your first one!",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(visibleEntries, key = { it.id }) { item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500)) +
                                slideInHorizontally(initialOffsetX = { -50 }) +
                                scaleIn(),
                        exit = fadeOut(animationSpec = tween(300)) +
                                slideOutVertically(targetOffsetY = { -100 })
                    ) {
                        DiaryCard(text = item.text)
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }

        // Toggle show button
        if (entries.size > 1) {
            Button(
                onClick = { showAll = !showAll },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(if (showAll) "Show Less" else "Show All Entries")
            }
        }

        Spacer(Modifier.height(10.dp))

        // Input
        OutlinedTextField(
            value = entry,
            onValueChange = { entry = it },
            label = { Text("Write your diary entry...") },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(10.dp))
                .background(Color(0xFFFFFCF3)),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF1F2A44),
                lineHeight = 24.sp
            )
        )

        Spacer(Modifier.height(16.dp))

        // Write button
        Button(
            onClick = {
                if (entry.trim().isNotEmpty()) {
                    entries = entries + DiaryEntry(nextId, entry)
                    nextId++
                    entry = ""
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shadow(6.dp, RoundedCornerShape(12.dp))
        ) {
            Text("Write", fontSize = 16.sp)
        }
    }
}

@Composable
fun DiaryCard(text: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .padding(horizontal = 2.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            // Red line
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Color(0xFFE96A6A))
                    .align(Alignment.CenterStart)
            )
            // Pin
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(0xFFD94F4F), shape = CircleShape)
                    .align(Alignment.TopStart)
            )
            Text(
                text,
                fontSize = 16.sp,
                color = Color(0xFF3C3A37),
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}
