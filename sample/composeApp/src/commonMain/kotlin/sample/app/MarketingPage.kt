package sample.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarketingPage(
    onNavigateBack: () -> Unit
) {
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
            Text("📢 Marketing", fontSize = 22.sp, color = Color(0xFF1F2A44))
            Button(onClick = onNavigateBack) {
                Text("←")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Marketing sections
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Feature highlights section
            item {
                MarketingCard(
                    title = "✨ Premium Features",
                    icon = "🌟",
                    content = {
                        Column {
                            MarketingFeature(
                                icon = "🔒",
                                title = "Secure Cloud Sync",
                                description = "Your diary entries are safely backed up and synced across all your devices."
                            )
                            Spacer(Modifier.height(12.dp))
                            MarketingFeature(
                                icon = "🎨",
                                title = "Custom Themes",
                                description = "Personalize your diary with beautiful themes and custom colors."
                            )
                            Spacer(Modifier.height(12.dp))
                            MarketingFeature(
                                icon = "📊",
                                title = "Advanced Analytics",
                                description = "Track your mood patterns and writing habits with detailed insights."
                            )
                        }
                    }
                )
            }

            // Pricing section
            item {
                MarketingCard(
                    title = "💰 Pricing Plans",
                    icon = "💳",
                    content = {
                        Column {
                            PricingPlan(
                                name = "Free",
                                price = "$0",
                                period = "forever",
                                features = listOf(
                                    "Basic diary entries",
                                    "Local storage",
                                    "Simple themes"
                                ),
                                isPopular = false
                            )
                            Spacer(Modifier.height(12.dp))
                            PricingPlan(
                                name = "Premium",
                                price = "$4.99",
                                period = "month",
                                features = listOf(
                                    "Everything in Free",
                                    "Cloud sync",
                                    "Advanced themes",
                                    "Analytics",
                                    "Export options"
                                ),
                                isPopular = true
                            )
                        }
                    }
                )
            }

            // Testimonials section
            item {
                MarketingCard(
                    title = "💬 What Users Say",
                    icon = "⭐",
                    content = {
                        Column {
                            Testimonial(
                                text = "This diary app has completely changed how I reflect on my day. The interface is beautiful and intuitive!",
                                author = "Sarah M.",
                                rating = 5
                            )
                            Spacer(Modifier.height(12.dp))
                            Testimonial(
                                text = "I love the cloud sync feature. I can write on my phone during the day and continue on my tablet at night.",
                                author = "Mike R.",
                                rating = 5
                            )
                            Spacer(Modifier.height(12.dp))
                            Testimonial(
                                text = "The analytics help me understand my mood patterns. It's like having a personal therapist!",
                                author = "Emma L.",
                                rating = 5
                            )
                        }
                    }
                )
            }

            // Call to action section
            item {
                MarketingCard(
                    title = "🚀 Get Started Today",
                    icon = "🎯",
                    content = {
                        Column {
                            Text(
                                "Join thousands of users who have transformed their daily reflection habits with our premium diary app.",
                                fontSize = 16.sp,
                                color = Color(0xFF666666),
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { /* Handle upgrade */ },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFD94F4F)
                                    )
                                ) {
                                    Text("Upgrade to Premium", color = Color.White)
                                }

                                OutlinedButton(
                                    onClick = { /* Handle learn more */ },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Learn More")
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MarketingCard(
    title: String,
    icon: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(15.dp))
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    icon,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    title,
                    fontSize = 18.sp,
                    color = Color(0xFF1F2A44),
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun MarketingFeature(
    icon: String,
    title: String,
    description: String
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(
            icon,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 12.dp, top = 2.dp)
        )
        Column {
            Text(
                title,
                fontSize = 16.sp,
                color = Color(0xFF1F2A44),
                fontWeight = FontWeight.Medium
            )
            Text(
                description,
                fontSize = 14.sp,
                color = Color(0xFF666666),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun PricingPlan(
    name: String,
    price: String,
    period: String,
    features: List<String>,
    isPopular: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular) Color(0xFFF8F4E6) else Color(0xFFFFFCF3)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (isPopular) 3.dp else 1.dp, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (isPopular) {
                Text(
                    "MOST POPULAR",
                    fontSize = 12.sp,
                    color = Color(0xFFD94F4F),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                name,
                fontSize = 18.sp,
                color = Color(0xFF1F2A44),
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    price,
                    fontSize = 24.sp,
                    color = Color(0xFFD94F4F),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "/$period",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                )
            }

            Column {
                features.forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            "✓",
                            fontSize = 14.sp,
                            color = Color(0xFF4CAF50),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            feature,
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Testimonial(
    text: String,
    author: String,
    rating: Int
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4E6)),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                repeat(rating) {
                    Text("⭐", fontSize = 16.sp, modifier = Modifier.padding(end = 2.dp))
                }
            }

            Text(
                "\"$text\"",
                fontSize = 14.sp,
                color = Color(0xFF666666),
                lineHeight = 20.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                "- $author",
                fontSize = 12.sp,
                color = Color(0xFF999999),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

