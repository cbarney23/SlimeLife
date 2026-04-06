package com.example.slimelife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {         // Applies default styling
                GameScreen()        // Calls the main UI function
            }
        }
    }
}

@Composable
fun GameScreen() {
    Column(             // Stacks UI vertically
        modifier = Modifier
            .fillMaxSize()                          // Fit whole screen
            .background(Color(color = 0xFF1E1E2E))    // Sets background color (dark)
            .padding(all = 16.dp)                     // Adds space around edges
    ) {
        TopHUD()            // Top bar of UI

        Spacer(modifier = Modifier.height(height = 16.dp))

        MainGameArea(      // Main Game Screen
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        ActionButtons()     // Interaction Buttons

        Spacer(modifier = Modifier.height(height = 16.dp))

        BottomMenu()
    }
}

@Composable
fun TopHUD() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF313244))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all=16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Slime Life",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text(                               // Tracker for pet happiness. (NOT IMPLEMENTED)
                    text = "Pet Happiness: 100",
                    color = Color.Yellow,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(width=12.dp))
                Text(
                    text = "Pet Hunger: 80",       // Tracker for pet hunger. (NOT IMPLEMENTED)
                    color = Color.Cyan,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun MainGameArea(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(color=0xFF45475A)),
        shape = RoundedCornerShape(size=20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Character / Game Scene Here",           // MAIN GAME SCREEN
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { }) {
            Text(text="Feed")                   // Feed pet. NOT IMPLEMENTED
        }

        Button(onClick = { }) {
            Text(text="Clean")                  // Clean Pet. NOT IMPLEMENTED
        }

        Button(onClick = { }) {
            Text(text="Pet")                    // Pet the slime. NOT IMPLEMENTED
        }
    }
}

@Composable
fun BottomMenu() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF313244))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all=12.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = { }) {
                Text(text="Home")
            }
            TextButton(onClick = { }) {
                Text(text="Settings")
            }
        }
    }
}