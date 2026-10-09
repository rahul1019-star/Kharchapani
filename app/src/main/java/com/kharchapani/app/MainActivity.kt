package com.kharchapani.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
            KharchaPaniApp()
        }
    }
}

@Composable
fun KharchaPaniApp() {
    var points by remember { mutableIntStateOf(1000) }
    var message by remember {
        mutableStateOf("Apna game chuniye!")
    }

    val games = listOf(
        "✈️ Aviator Demo",
        "🎨 Colour Lottery",
        "🔢 Number Lottery",
        "📊 Big / Small"
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F5FA))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "KharchaPani",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2346A0)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2346A0)
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        "Aapke Virtual Points",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        "$points",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Demo points only",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Games",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            games.forEach { game ->
                Button(
                    onClick = {
                        message = "$game selected. Demo mode only!"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(game, fontSize = 17.sp)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = message,
                fontSize = 15.sp,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    points = 1000
                    message = "Virtual points reset ho gaye!"
                }
            ) {
                Text("Reset Demo Points")
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "No real money • No cash withdrawal",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
