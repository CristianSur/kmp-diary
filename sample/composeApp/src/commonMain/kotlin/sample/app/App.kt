// App.kt
package sample.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class DiaryEntry(val id: Int, val text: String)

enum class AppPage {
    NOTE, PROFILE, MARKETING
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var currentPage by remember { mutableStateOf(AppPage.NOTE) }
    var userName by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf(listOf<DiaryEntry>()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE7D5)),
        containerColor = Color(0xFFEDE7D5),
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentPage,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = {
                when {
                    targetState == AppPage.NOTE -> {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(200, easing = EaseOutCubic)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(200, easing = EaseInCubic)
                        )
                    }

                    else -> {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(200, easing = EaseOutCubic)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(200, easing = EaseInCubic)
                        )
                    }
                }
            },
            label = "page_transition"
        ) { page ->
            when (page) {
                AppPage.NOTE -> NotePage(
                    onNavigateToProfile = { currentPage = AppPage.PROFILE },
                    onNavigateToMarketing = { currentPage = AppPage.MARKETING },
                    userName = userName,
                    entries = entries,
                    onEntriesChanged = { entries = it }
                )

                AppPage.PROFILE -> ProfilePage(
                    onNavigateBack = { currentPage = AppPage.NOTE },
                    userName = userName,
                    onUserNameChanged = { userName = it },
                    entries = entries
                )

                AppPage.MARKETING -> MarketingPage(
                    onNavigateBack = { currentPage = AppPage.NOTE }
                )
            }
        }
    }
}
