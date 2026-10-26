package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CoralFlame
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkOutline
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.viewmodel.CalisthenicsViewModel
import com.example.ui.viewmodel.StandaloneTimerMode
import com.example.ui.viewmodel.StandaloneTimerPhase

@Composable
fun TimersScreen(
    viewModel: CalisthenicsViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val timerState by viewModel.timerState.collectAsState()

    val modes = listOf(
        StandaloneTimerMode.TABATA to "TABATA",
        StandaloneTimerMode.EMOM to "EMOM",
        StandaloneTimerMode.AMRAP to "AMRAP",
        StandaloneTimerMode.CRONOMETRO to "CRONÔMETRO"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Cronômetros & Intervalos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Temporizadores especializados para treinos de alta intensidade",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )
        }

        // Mode Selector Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            modes.forEach { (mode, label) ->
                val isSelected = timerState.mode == mode
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) AmberGold else DarkSurfaceCard,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) AmberGold else DarkOutline
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setTimerMode(mode) }
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.Black else Color.White
                        )
                    }
                }
            }
        }

        // Timer Phase & Info Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = when (timerState.phase) {
                StandaloneTimerPhase.PREPARAR -> AmberGold.copy(alpha = 0.2f)
                StandaloneTimerPhase.WORK -> EmeraldSuccess.copy(alpha = 0.2f)
                StandaloneTimerPhase.REST -> CyanAccent.copy(alpha = 0.2f)
                StandaloneTimerPhase.FINISHED -> CoralFlame.copy(alpha = 0.2f)
            }
        ) {
            Text(
                text = when (timerState.phase) {
                    StandaloneTimerPhase.PREPARAR -> "PREPARE-SE..."
                    StandaloneTimerPhase.WORK -> "EM ATIVIDADE (WORK)"
                    StandaloneTimerPhase.REST -> "DESCANSO (REST)"
                    StandaloneTimerPhase.FINISHED -> "INTERVALO FINALIZADO!"
                },
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black,
                color = when (timerState.phase) {
                    StandaloneTimerPhase.PREPARAR -> AmberGold
                    StandaloneTimerPhase.WORK -> EmeraldSuccess
                    StandaloneTimerPhase.REST -> CyanAccent
                    StandaloneTimerPhase.FINISHED -> CoralFlame
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Big Circular Countdown Graphic
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(240.dp)
                .padding(8.dp)
        ) {
            val totalSec = when (timerState.phase) {
                StandaloneTimerPhase.PREPARAR -> 5
                StandaloneTimerPhase.WORK -> timerState.workSeconds.coerceAtLeast(1)
                StandaloneTimerPhase.REST -> timerState.restSeconds.coerceAtLeast(1)
                StandaloneTimerPhase.FINISHED -> 1
            }
            val fraction = if (timerState.mode == StandaloneTimerMode.CRONOMETRO) {
                ((timerState.secondsLeft % 60) / 60f)
            } else {
                (timerState.secondsLeft.toFloat() / totalSec).coerceIn(0f, 1f)
            }

            CircularProgressIndicator(
                progress = { fraction },
                modifier = Modifier.fillMaxSize(),
                color = when (timerState.phase) {
                    StandaloneTimerPhase.WORK -> EmeraldSuccess
                    StandaloneTimerPhase.REST -> CyanAccent
                    else -> AmberGold
                },
                trackColor = DarkOutline,
                strokeWidth = 14.dp
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val displayMin = timerState.secondsLeft / 60
                val displaySec = timerState.secondsLeft % 60

                Text(
                    text = if (timerState.mode == StandaloneTimerMode.CRONOMETRO || timerState.secondsLeft >= 60) {
                        "%02d:%02d".format(displayMin, displaySec)
                    } else {
                        "${timerState.secondsLeft}"
                    },
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 58.sp),
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )

                if (timerState.mode != StandaloneTimerMode.CRONOMETRO) {
                    Text(
                        text = "Round ${timerState.currentRound} de ${timerState.totalRounds}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberGold
                    )
                } else {
                    Text(
                        text = "Tempo Corrido",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        // Controls: Start/Pause, Reset
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.resetTimer() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .testTag("reset_timer_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Resetar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Resetar", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.startPauseTimer() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (timerState.isRunning) CoralFlame else AmberGold
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .testTag("start_pause_timer_button")
            ) {
                Icon(
                    imageVector = if (timerState.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (timerState.isRunning) "Pausar" else "Iniciar",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (timerState.isRunning) "Pausar" else "Iniciar",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Explanatory card for current mode
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkOutline),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = when (timerState.mode) {
                        StandaloneTimerMode.TABATA -> "Como funciona o Tabata:"
                        StandaloneTimerMode.EMOM -> "Como funciona o EMOM:"
                        StandaloneTimerMode.AMRAP -> "Como funciona o AMRAP:"
                        StandaloneTimerMode.CRONOMETRO -> "Cronômetro Livre:"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = AmberGold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (timerState.mode) {
                        StandaloneTimerMode.TABATA -> "20 segundos de esforço máximo + 10 segundos de descanso total, repetidos por 8 rounds (4 minutos). Excelente para queima de gordura e capacidade cardiovascular."
                        StandaloneTimerMode.EMOM -> "A cada minuto no relógio (Every Minute On the Minute), execute a meta do exercício. O tempo que sobrar até completar o minuto é o seu descanso antes do próximo round."
                        StandaloneTimerMode.AMRAP -> "O máximo de repetições ou rounds possíveis dentro do tempo estipulado. Mantenha um ritmo sustentável e anote seu número final de rounds."
                        StandaloneTimerMode.CRONOMETRO -> "Cronômetro progressivo ideal para medir sustentação de isometrias (Planche, L-Sit, Prancha) ou descanso personalizado entre séries."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1)
                )
            }
        }
    }
}
