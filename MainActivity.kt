package com.kharchapani.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

private val Ink = Color(0xFF101827)
private val Panel = Color(0xFF1B2739)
private val Lime = Color(0xFFB7F36B)
private val Muted = Color(0xFF9AABC1)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KharchaPaniApp() }
    }
}

@Composable
fun KharchaPaniApp() {
    var screen by remember { mutableStateOf("home") }
    var points by remember { mutableIntStateOf(0) }
    var quizIndex by remember { mutableIntStateOf(0) }
    var quizCorrect by remember { mutableIntStateOf(0) }
    var quizAnswered by remember { mutableStateOf(false) }
    var feedback by remember { mutableStateOf("") }
    var mathA by remember { mutableIntStateOf(4) }
    var mathB by remember { mutableIntStateOf(5) }
    var mathAnswered by remember { mutableStateOf(false) }
    var memoryOrder by remember { mutableStateOf(listOf(2, 4, 1)) }
    var memoryGuess by remember { mutableStateOf("") }
    var word by remember { mutableStateOf("KHEL") }
    var wordAnswered by remember { mutableStateOf(false) }
    var reactionReady by remember { mutableStateOf(false) }
    var reactionMessage by remember { mutableStateOf("Tap start, then tap the target.") }
    var dailyClaimed by remember { mutableStateOf(false) }

    val quiz = listOf(
        Triple("India ki rajdhani?", listOf("Jaipur", "New Delhi", "Mumbai"), 1),
        Triple("Ek hafte mein kitne din?", listOf("5", "7", "9"), 1),
        Triple("Suraj kis disha se ugta hai?", listOf("East", "West", "North"), 0),
        Triple("12 × 3 kitna?", listOf("24", "36", "42"), 1),
        Triple("Paani ka formula?", listOf("CO2", "H2O", "O2"), 1)
    )

    MaterialTheme(colorScheme = darkColorScheme(
        primary = Lime, background = Ink, surface = Panel,
        onPrimary = Ink, onBackground = Color.White, onSurface = Color.White
    )) {
        Scaffold(containerColor = Ink, bottomBar = {
            NavigationBar(containerColor = Ink, contentColor = Lime) {
                NavigationBarItem(selected = screen == "home", onClick = { screen = "home" },
                    icon = { Text("⌂", fontSize = 20.sp) }, label = { Text("Home") })
                NavigationBarItem(selected = screen == "wallet", onClick = { screen = "wallet" },
                    icon = { Text("★", fontSize = 20.sp) }, label = { Text("Points") })
            }
        }) { pad ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(pad).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Box(Modifier.size(48.dp).background(Lime, RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center) {
                            Text("KP", color = Ink, fontWeight = FontWeight.Black, fontSize = 20.sp)
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Kharcha Pani", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                            Text("Play • Learn • Collect points", color = Muted, fontSize = 12.sp)
                        }
                        Text("★ $points", color = Lime, fontWeight = FontWeight.Bold)
                    }
                }
                if (screen == "home") {
                    item {
                        Column(Modifier.fillMaxWidth().background(Lime, RoundedCornerShape(24.dp)).padding(20.dp)) {
                            Text("WELCOME, PLAYER!", color = Ink.copy(alpha = .7f), fontWeight = FontWeight.Bold)
                            Text("Thoda khelo.\\nDimag lagao.", color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Black)
                            Text("$points KP points", color = Ink, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Text("Aaj ke 5 games", fontSize = 19.sp, fontWeight = FontWeight.Bold) }
                    item { GameCard("🧠", "Quick Quiz", "5 general knowledge questions") { quizIndex = 0; quizCorrect = 0; quizAnswered = false; feedback = ""; screen = "quiz" } }
                    item { GameCard("➗", "Math Sprint", "Solve a quick arithmetic question") { mathA = Random.nextInt(2, 12); mathB = Random.nextInt(2, 12); mathAnswered = false; feedback = ""; screen = "math" } }
                    item { GameCard("🧩", "Memory Challenge", "Remember the number sequence") { memoryOrder = List(3) { Random.nextInt(1, 6) }; memoryGuess = ""; feedback = ""; screen = "memory" } }
                    item { GameCard("🔤", "Word Scramble", "Unscramble a simple word") { word = listOf("KHEL", "DOST", "GAME", "JEET").random(); wordAnswered = false; memoryGuess = ""; feedback = ""; screen = "word" } }
                    item { GameCard("⚡", "Reaction Tap", "Tap the target to score a point") { reactionReady = false; reactionMessage = "Tap the target button to earn a practice point."; screen = "reaction" } }
                    item {
                        Button(onClick = { if (!dailyClaimed) { points += 5; dailyClaimed = true } }, modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Panel, contentColor = Lime)) {
                            Text(if (dailyClaimed) "Daily bonus claimed ✓" else "🎁 Claim daily +5 points")
                        }
                    }
                    item {
                        Text("Demo only: points have no cash value. No deposits, wagers, cash prizes or withdrawals are included.",
                            color = Muted, fontSize = 12.sp)
                    }
                } else if (screen == "wallet") {
                    item {
                        Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(20.dp)) {
                            Text("Your points wallet", color = Muted)
                            Text("$points KP", fontSize = 36.sp, color = Lime, fontWeight = FontWeight.Black)
                            Text("Practice points only — not money and not redeemable.", color = Muted, fontSize = 13.sp)
                        }
                    }
                    item { Text("Prototype status", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                    item { Text("• 5 mini-games\\n• Local session score\\n• No account/server\\n• No real ads or payments\\n• No cash-out or redeemable rewards", lineHeight = 25.sp) }
                } else {
                    item { TextButton(onClick = { screen = "home" }) { Text("← Back to Home", color = Lime) } }
                    item {
                        Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(18.dp)) {
                            when (screen) {
                                "quiz" -> {
                                    if (quizIndex < quiz.size) {
                                        Text("QUESTION ${quizIndex + 1}/${quiz.size}", color = Lime, fontWeight = FontWeight.Bold)
                                        Spacer(Modifier.height(12.dp))
                                        Text(quiz[quizIndex].first, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                        Spacer(Modifier.height(10.dp))
                                        quiz[quizIndex].second.forEachIndexed { i, answer ->
                                            Button(onClick = {
                                                if (!quizAnswered) {
                                                    quizAnswered = true
                                                    if (i == quiz[quizIndex].third) { points += 10; quizCorrect++; feedback = "Correct! +10 points" }
                                                    else feedback = "Correct answer: ${quiz[quizIndex].second[quiz[quizIndex].third]}"
                                                }
                                            }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                                enabled = !quizAnswered, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF30445A))) { Text(answer) }
                                        }
                                        Text(feedback, color = Lime)
                                        if (quizAnswered) Button(onClick = {
                                            if (quizIndex == quiz.lastIndex) { feedback = "Quiz complete: $quizCorrect/${quiz.size} correct."; screen = "quizResult" }
                                            else { quizIndex++; quizAnswered = false; feedback = "" }
                                        }) { Text(if (quizIndex == quiz.lastIndex) "See result" else "Next") }
                                    } else Text("Quiz complete")
                                }
                                "quizResult" -> { Text("Quiz complete!", fontSize = 24.sp, fontWeight = FontWeight.Bold); Text("$quizCorrect/${quiz.size} correct answers."); Button(onClick = { screen = "home" }) { Text("More games") } }
                                "math" -> {
                                    Text("Math Sprint", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                    Text("$mathA + $mathB = ?", fontSize = 28.sp)
                                    listOf(mathA + mathB, mathA + mathB + 2, mathA + mathB - 1).shuffled().forEach { value ->
                                        Button(onClick = { if (!mathAnswered) { mathAnswered = true; if (value == mathA + mathB) { points += 5; feedback = "Correct! +5 points" } else feedback = "Try again next round." } },
                                            enabled = !mathAnswered, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text(value.toString()) }
                                    }
                                    Text(feedback, color = Lime)
                                    Button(onClick = { mathA = Random.nextInt(2,12); mathB = Random.nextInt(2,12); mathAnswered = false; feedback = "" }) { Text("New question") }
                                }
                                "memory" -> {
                                    Text("Memory Challenge", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                    Text("Remember this sequence: ${memoryOrder.joinToString("  ")}", fontSize = 23.sp)
                                    OutlinedTextField(value = memoryGuess, onValueChange = { memoryGuess = it }, label = { Text("Type sequence, e.g. 241") })
                                    Button(onClick = { if (memoryGuess.filter { it.isDigit() } == memoryOrder.joinToString("")) { points += 10; feedback = "Great memory! +10 points" } else feedback = "Not quite. Sequence was ${memoryOrder.joinToString("")}" }) { Text("Check") }
                                    Text(feedback, color = Lime)
                                    Button(onClick = { memoryOrder = List(3) { Random.nextInt(1,6) }; memoryGuess = ""; feedback = "" }) { Text("New sequence") }
                                }
                                "word" -> {
                                    val scrambled = word.reversed()
                                    Text("Word Scramble", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                    Text("Unscramble: $scrambled", fontSize = 27.sp)
                                    OutlinedTextField(value = memoryGuess, onValueChange = { memoryGuess = it.uppercase() }, label = { Text("Your answer") })
                                    Button(onClick = { if (!wordAnswered) { wordAnswered = true; if (memoryGuess.trim() == word) { points += 10; feedback = "Correct! +10 points" } else feedback = "Answer: $word" } }) { Text("Check word") }
                                    Text(feedback, color = Lime)
                                    Button(onClick = { word = listOf("KHEL", "DOST", "GAME", "JEET").random(); memoryGuess = ""; feedback = ""; wordAnswered = false }) { Text("New word") }
                                }
                                "reaction" -> {
                                    Text("Reaction Tap", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                    Text(reactionMessage)
                                    Button(onClick = { if (!reactionReady) { reactionReady = true; reactionMessage = "Target tapped! +5 practice points."; points += 5 } else { reactionMessage = "You already scored this round. Start a new round."; } }, modifier = Modifier.fillMaxWidth()) { Text(if (reactionReady) "Scored ✓" else "TAP TARGET") }
                                    Button(onClick = { reactionReady = false; reactionMessage = "Ready for another round." }) { Text("New round") }
                                }
                                else -> Text("Choose a game from Home.")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameCard(emoji: String, title: String, description: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(18.dp)).padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 30.sp)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(description, color = Muted, fontSize = 12.sp)
        }
        Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = Lime, contentColor = Ink)) { Text("Play") }
    }
}
