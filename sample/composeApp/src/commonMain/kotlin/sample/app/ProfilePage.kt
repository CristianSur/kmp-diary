package sample.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfilePage(
    onNavigateBack: () -> Unit,
    userName: String,
    onUserNameChanged: (String) -> Unit,
    entries: List<DiaryEntry>
) {
    var nameInput by remember { mutableStateOf(userName) }
    var isSaved by remember { mutableStateOf(false) }

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
            Text("👤 Profile", fontSize = 22.sp, color = Color(0xFF1F2A44))
            Button(onClick = onNavigateBack) {
                Text("←")
            }
        }

        Spacer(Modifier.height(40.dp))

        // Profile content
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
                Text(
                    "Your Name",
                    fontSize = 18.sp,
                    color = Color(0xFF1F2A44),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TextField(
                    value = nameInput,
                    onValueChange = {
                        nameInput = it
                        isSaved = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    placeholder = { Text("Enter your name...") },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFF1F2A44)
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color(0xFFD94F4F),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFD94F4F),
                        unfocusedIndicatorColor = Color(0xFFE0E0E0),
                        disabledIndicatorColor = Color(0xFFE0E0E0),
                        focusedPlaceholderColor = Color(0xFF9E9E9E),
                        unfocusedPlaceholderColor = Color(0xFF9E9E9E),
                        disabledPlaceholderColor = Color(0xFF9E9E9E)
                    )
                )

                Button(
                    onClick = {
                        onUserNameChanged(nameInput.trim())
                        isSaved = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD94F4F)
                    )
                ) {
                    Text(
                        if (isSaved) "✓ Saved!" else "Save Name",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                if (userName.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Current name: $userName",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Statistics section
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
                Text(
                    "📊 Statistics",
                    fontSize = 18.sp,
                    color = Color(0xFF1F2A44),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Total notes count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total Notes:",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        "${entries.size}",
                        fontSize = 16.sp,
                        color = Color(0xFF1F2A44),
                        fontWeight = FontWeight.Bold
                    )
                }

                if (entries.isEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "No notes created yet",
                        fontSize = 14.sp,
                        color = Color(0xFF999999),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Additional info
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFCF3)),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(1.dp, RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "💡 Tip",
                    fontSize = 16.sp,
                    color = Color(0xFF1F2A44),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Your name will appear in the diary header and personalize your experience.",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

