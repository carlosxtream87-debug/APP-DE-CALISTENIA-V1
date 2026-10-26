package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.screens.ActiveWorkoutScreen
import com.example.ui.screens.ExercisesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileProgressScreen
import com.example.ui.screens.SkillsScreen
import com.example.ui.screens.TimersScreen
import com.example.ui.screens.WorkoutsScreen
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkOutline
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.CalisthenicsViewModel
import com.example.ui.viewmodel.ScreenDestination

data class NavigationBarNavItem(
    val destination: ScreenDestination,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

class MainActivity : ComponentActivity() {

    private val viewModel: CalisthenicsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CalisthenixApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CalisthenixApp(viewModel: CalisthenicsViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    BackHandler(enabled = currentScreen != ScreenDestination.Home) {
        viewModel.handleBackPress()
    }

    val navItems = listOf(
        NavigationBarNavItem(
            destination = ScreenDestination.Home,
            label = "Início",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            testTag = "nav_home"
        ),
        NavigationBarNavItem(
            destination = ScreenDestination.Workouts,
            label = "Treinos",
            selectedIcon = Icons.Filled.FitnessCenter,
            unselectedIcon = Icons.Outlined.FitnessCenter,
            testTag = "nav_workouts"
        ),
        NavigationBarNavItem(
            destination = ScreenDestination.Skills,
            label = "Skills",
            selectedIcon = Icons.Filled.EmojiEvents,
            unselectedIcon = Icons.Outlined.EmojiEvents,
            testTag = "nav_skills"
        ),
        NavigationBarNavItem(
            destination = ScreenDestination.Timers,
            label = "Timers",
            selectedIcon = Icons.Filled.Timer,
            unselectedIcon = Icons.Outlined.Timer,
            testTag = "nav_timers"
        ),
        NavigationBarNavItem(
            destination = ScreenDestination.Profile,
            label = "Evolução",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            testTag = "nav_profile"
        )
    )

    val showBottomBar = currentScreen != ScreenDestination.ActiveWorkout

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = DarkSurface,
                    contentColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    navItems.forEach { item ->
                        val isSelected = currentScreen == item.destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(item.destination) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black,
                                selectedTextColor = AmberGold,
                                indicatorColor = AmberGold,
                                unselectedIconColor = Color(0xFF94A3B8),
                                unselectedTextColor = Color(0xFF94A3B8)
                            ),
                            modifier = Modifier.testTag(item.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                ScreenDestination.Home -> HomeScreen(
                    viewModel = viewModel,
                    onNavigate = { viewModel.navigateTo(it) }
                )
                ScreenDestination.Workouts -> WorkoutsScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.handleBackPress() }
                )
                ScreenDestination.ActiveWorkout -> ActiveWorkoutScreen(
                    viewModel = viewModel,
                    onFinish = { viewModel.navigateTo(ScreenDestination.Home) }
                )
                ScreenDestination.Skills -> SkillsScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.handleBackPress() }
                )
                ScreenDestination.Exercises -> ExercisesScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.handleBackPress() }
                )
                ScreenDestination.Timers -> TimersScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.handleBackPress() }
                )
                ScreenDestination.Profile -> ProfileProgressScreen(
                    viewModel = viewModel,
                    onBack = { viewModel.handleBackPress() }
                )
            }
        }
    }
}
