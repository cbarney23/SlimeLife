package com.example.slimelife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = GamePrefs(this) // prefs is our game data

        setContent {
            MaterialTheme {         // Applies default styling
                GameScreen(prefs)        // Calls the main UI function
            }
        }
    }
}

@Composable
fun GameScreen(prefs : GamePrefs) {
    var hunger by remember { mutableStateOf(prefs.getHunger()) }
    var happiness by remember { mutableStateOf(prefs.getHappiness()) }
    var isFedHappy by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)

            val now = Instant.now().epochSecond

            val hungerElapsed = now - prefs.getLastFeed()
            val happinessElapsed = now - prefs.getLastPet()

            val hungerLoss = hungerElapsed
            val happinessLoss = happinessElapsed

            hunger = (prefs.getHunger() - hungerLoss).coerceAtLeast(0)
            happiness = (prefs.getHappiness() - happinessLoss).coerceAtLeast(0)
        }
    }

    LaunchedEffect(isFedHappy) {
        if (isFedHappy) {
            kotlinx.coroutines.delay(2000)
            isFedHappy = false
        }
    }

    Column(             // Stacks UI vertically
        modifier = Modifier
            .fillMaxSize()                          // Fit whole screen
            .background(Color(color = 0xFFF5E6D3))    // Sets background color (tan)
            .padding(all = 16.dp)                     // Adds space around edges
    ) {
        TopHUD(hunger, happiness)            // Top bar of UI

        Spacer(modifier = Modifier.height(height = 16.dp))

        MainGameArea(      // Main Game Screen
            isFedHappy = isFedHappy,
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        ActionButtons(prefs, hunger, happiness, onFeed = { isFedHappy = true })     // Interaction Buttons

        Spacer(modifier = Modifier.height(height = 16.dp))

        BottomMenu()
    }

}

@Composable
fun TopHUD(hunger : Long, happiness : Long) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8D5B7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Slime Life",
                color = Color(0xFF3B2F2F),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Happiness: " + happiness.coerceAtLeast(0),
                    color = Color.Yellow,
                    fontSize = 13.sp
                )
                Text(
                    text = "Hunger: " + hunger.coerceAtLeast(0),
                    color = Color.Cyan,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun MainGameArea(
    isFedHappy: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8D5B7)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(260.dp),
                contentAlignment = Alignment.Center
            ) {
                // Base slime
                Image(
                    painter = painterResource(id = R.drawable.blue),
                    contentDescription = "Slime body",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Eyes
                Image(
                    painter = painterResource(
                        id = if (isFedHappy) R.drawable.happy else R.drawable.eyes
                    ),
                    contentDescription = "Slime eyes",
                    modifier = Modifier
                        .fillMaxWidth(0.42f)
                        .offset(y = 20.dp),
                    contentScale = ContentScale.Fit
                )

                // Blush when happy
                if (isFedHappy) {
                    Image(
                        painter = painterResource(id = R.drawable.blush),
                        contentDescription = "Slime blush",
                        modifier = Modifier
                            .fillMaxWidth(0.62f)
                            .offset(y = 36.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButtons(prefs : GamePrefs, hunger : Long, happiness: Long, onFeed: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            prefs.setHunger(100)
            prefs.setLastFeed(Instant.now())
            onFeed()
        }) {
            Text(text="Feed")                   // Feed pet. NOT IMPLEMENTED
        }

        Button(onClick = { }) {
            Text(text="Clean")                  // Clean Pet. NOT IMPLEMENTED
        }

        Button(onClick = {
            prefs.setHappiness(100)
            prefs.setLastPet(Instant.now())
        }) {
            Text(text="Pet")                    // Pet the slime. NOT IMPLEMENTED
        }
    }
}

@Composable
fun BottomMenu() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8D5B7))
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