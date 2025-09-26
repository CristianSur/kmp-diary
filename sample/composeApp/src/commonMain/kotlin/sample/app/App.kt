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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class DiaryEntry(val id: Int, val text: String)


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    NotePage()
}

@Composable
fun NotePage() {
    var entry by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf(listOf<DiaryEntry>()) }
    var showAll by remember { mutableStateOf(false) }
    var nextId by remember { mutableStateOf(0) }

    var isVisible by remember { mutableStateOf(false) }

    val visibleEntries = remember(entries, showAll) {
        if (showAll) entries else entries.takeLast(1)
    }

    fun addEntry(text: String) {
        isVisible = false
        CoroutineScope(Dispatchers.Main).launch {
            if (entries.isNotEmpty())
                delay(600) // same as exit animation duration
            entries = entries + DiaryEntry(nextId, text)
            nextId++
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7D5))
            .padding(32.dp)
            .padding(top = 40.dp)
            .imePadding()
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
                    var isRevealing by remember { mutableStateOf(false) }

                    // Reveal animation from left to right (0% to 100%)
                    val revealProgress by animateFloatAsState(
                        targetValue = if (isRevealing) 1f else 0f,
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = EaseOutCubic
                        ),
                        label = "reveal"
                    )

                    LaunchedEffect(Unit) {
                        delay(200) // Small delay before reveal starts
                        isRevealing = true
                    }


                    // Start reveal animation when the item enters composition
                    LaunchedEffect(item.id) {
                        isVisible = true
                    }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(
                            animationSpec = tween(600)
                        ) + expandHorizontally(
                            expandFrom = Alignment.Start,
                            animationSpec = tween(600, easing = EaseOutCubic)
                        ),
                        exit = fadeOut(
                            animationSpec = tween(600)
                        ) + shrinkHorizontally(
                            shrinkTowards = Alignment.End,
                            animationSpec = tween(600, easing = EaseInCubic)
                        )
                    ) {
                        DiaryCard(
                            text = item.text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp)
                        )
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
//Modifier.padding(16.dp)

//        Column(modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Bottom,
//            horizontalAlignment = Alignment.CenterHorizontally) {
        // Input
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .padding(bottom = 60.dp) // move above the TextField
                .height(75.dp)
                .shadow(2.dp, RoundedCornerShape(10.dp))
                .background(Color(0xFFFFFCF3))
                    .align(Alignment.BottomCenter)
        ) {
            // Red line
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = 50.dp) // move the line much further to the right
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(Color(0xFFE96A6A))
                    )
                }
                // Pin
                Box(
                    modifier = Modifier
                        .offset(x = 12.dp, y = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color(0xFFD94F4F), shape = CircleShape)
                    )
                }
            }

            TextField(
                value = entry,
                onValueChange = { entry = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 48.dp, end = 8.dp, bottom = 8.dp),
                placeholder = { Text("Write your diary entry...") },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF1F2A44),
                    lineHeight = 24.sp
                ),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color(0xFFD94F4F),
                    //Main box
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    //Bottom line
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    //Placeholder
                    focusedPlaceholderColor = Color(0xFF9E9E9E),
                    unfocusedPlaceholderColor = Color(0xFF9E9E9E),
                    disabledPlaceholderColor = Color(0xFF9E9E9E)
                )

            )
        }
        Spacer(Modifier.height(16.dp))

        // Write button
        Button(
            onClick = {
                if (entry.trim().isNotEmpty()) {
                    addEntry(entry)
                    entry = ""
                }
            },
            modifier = Modifier
                .padding(5.dp)
                .shadow(2.dp, RoundedCornerShape(18.dp))
                    .align(Alignment.BottomCenter)

        ) {
            Text("Write", fontSize = 16.sp)
        }
    }
}

}

@Composable
fun DiaryCard(text: String, modifier: Modifier) =
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
        modifier = modifier
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
