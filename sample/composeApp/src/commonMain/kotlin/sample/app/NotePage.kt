package sample.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NotePage(
    onNavigateToProfile: () -> Unit,
    onNavigateToMarketing: () -> Unit,
    userName: String,
    entries: List<DiaryEntry>,
    onEntriesChanged: (List<DiaryEntry>) -> Unit
) {
    var entry by remember { mutableStateOf("") }
    var showAll by remember { mutableStateOf(false) }
    var nextId by remember { mutableStateOf(0) }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var screenHeight by remember { mutableStateOf(0) }

    var isVisible by remember { mutableStateOf(false) }

    val visibleEntries = remember(entries, showAll) {
        if (showAll) entries else entries.takeLast(1)
    }



    fun addEntry(text: String) {
        isVisible = false
        CoroutineScope(Dispatchers.Main).launch {
            if (entries.isNotEmpty())
                delay(600) // same as exit animation duration
            onEntriesChanged(entries + DiaryEntry(nextId, text))
            nextId++
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7D5))
            .onSizeChanged { size ->
                // Detect keyboard by checking if available height decreased significantly
                val newHeight = size.height
                if (screenHeight > 0) {
                    val heightDifference = screenHeight - newHeight
                    isKeyboardVisible = heightDifference > 200 // Keyboard typically takes 200+ pixels
                }
                screenHeight = newHeight
            }
    ) {
        // TOP QUARTER - Completely fixed, no keyboard interaction
        Column(
            modifier = Modifier
                .weight(7f)
                .fillMaxWidth()
                .padding(32.dp)
                .padding(top = 40.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    if (userName.isNotEmpty()) "📔 ${userName}'s Diary" else "📔 My Diary",
                    fontSize = 22.sp,
                    color = Color(0xFF1F2A44)
                )
                Row {
                    Button(onClick = onNavigateToMarketing) {
                        Text("📢")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = onNavigateToProfile) {
                        Text("👤")
                    }
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
//                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

//            // Toggle show button
//            if (entries.size > 1) {
//                Box(
//                    modifier = Modifier
//                        .padding(horizontal = 2.dp)
//                        .padding(bottom = 4.dp)
//                        .shadow(1.dp, RoundedCornerShape(18.dp))
//                        .align(Alignment.CenterHorizontally)
//                ) {
//                    Button(
//                        onClick = { showAll = !showAll }, modifier = Modifier
//                    ) {
//                        Text(if (showAll) "Show Less" else "Show All Entries")
//                    }
//                }
//            }
        }

        // BOTTOM THREE QUARTERS - Input area with keyboard handling
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            // Input
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .height(75.dp)
                    .shadow(1.dp, RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFFCF3))

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

            // Write button
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = {
                        if (entry.trim().isNotEmpty()) {
                            addEntry(entry)
                            entry = ""
                        }
                    },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .shadow(1.dp, RoundedCornerShape(18.dp))

                ) {
                    Text("Write", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun DiaryCard(text: String, modifier: Modifier) =
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(10.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color(
                0xFFFFFCF3
            )
        ),
        modifier = modifier
    ) {
        Box(modifier = Modifier.defaultMinSize(minHeight = 48.dp)) {
            // Red line
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFFFFFCF3))
                    .offset(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(Color.Red)
                        .align(Alignment.CenterStart)
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
            Text(
                text,
                fontSize = 16.sp,
                color = Color(0xFF3C3A37),
                modifier = Modifier.padding(start = 64.dp, end = 8.dp, bottom = 8.dp),
                style = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF1F2A44),
                    lineHeight = 24.sp
                )
            )
        }
    }

