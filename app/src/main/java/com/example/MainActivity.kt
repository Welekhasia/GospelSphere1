package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.GospelSphereViewModel
import com.example.ui.components.AudioPlayerBar
import com.example.ui.screens.*
import com.example.ui.theme.GospelSphereTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GospelSphereTheme {
                GospelSphereMainApp()
            }
        }
    }
}

sealed class NavDestination(val route: String, val label: String, val icon: ImageVector) {
    object Home : NavDestination("home", "Home", Icons.Default.Home)
    object Music : NavDestination("music", "Music", Icons.Default.MusicNote)
    object Videos : NavDestination("videos", "Videos", Icons.Default.VideoLibrary)
    object Bible : NavDestination("bible", "Bible", Icons.Default.MenuBook)
    object Prayer : NavDestination("prayer", "Prayer", Icons.Default.VolunteerActivism)
    object Churches : NavDestination("churches", "Churches", Icons.Default.Church)
    object Artists : NavDestination("artists", "Artists", Icons.Default.Mic)
    object Marketplace : NavDestination("marketplace", "Store", Icons.Default.Storefront)
    object AiAssistant : NavDestination("ai_assistant", "AI Helper", Icons.Default.Psychology)
    object Profile : NavDestination("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GospelSphereMainApp() {
    val viewModel: GospelSphereViewModel = viewModel()
    var currentRoute by remember { mutableStateOf("home") }

    val primaryNavItems = listOf(
        NavDestination.Home,
        NavDestination.Music,
        NavDestination.Videos,
        NavDestination.Bible,
        NavDestination.Prayer,
        NavDestination.Profile
    )

    var showMoreMenuSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("gospelsphere_main_scaffold"),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Public,
                            contentDescription = "GospelSphere Logo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GospelSphere",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { currentRoute = "ai_assistant" },
                        modifier = Modifier.testTag("top_ai_assistant_icon")
                    ) {
                        Icon(
                            Icons.Default.Psychology,
                            contentDescription = "GospelSphere AI",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { showMoreMenuSheet = true },
                        modifier = Modifier.testTag("top_more_menu_icon")
                    ) {
                        Icon(Icons.Default.Apps, contentDescription = "Ecosystem Menu")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                // Audio Player Mini Controller
                AudioPlayerBar(viewModel = viewModel)

                // Primary Bottom Navigation Bar
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    primaryNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentRoute = item.route },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.label,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            label = {
                                Text(
                                    item.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.testTag("nav_item_${item.route}")
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
            when (currentRoute) {
                "home" -> HomeScreen(viewModel = viewModel, onNavigate = { currentRoute = it })
                "music" -> MusicScreen(viewModel = viewModel)
                "videos" -> VideoScreen(viewModel = viewModel)
                "bible" -> BibleScreen(viewModel = viewModel)
                "prayer" -> PrayerScreen(viewModel = viewModel)
                "churches" -> ChurchScreen(viewModel = viewModel, onNavigate = { currentRoute = it })
                "artists" -> ArtistScreen(viewModel = viewModel, onNavigate = { currentRoute = it })
                "marketplace" -> MarketplaceScreen(viewModel = viewModel)
                "ai_assistant" -> AiAssistantScreen(viewModel = viewModel)
                "profile" -> ProfileScreen(viewModel = viewModel)
                else -> HomeScreen(viewModel = viewModel, onNavigate = { currentRoute = it })
            }
        }

        // More Ecosystem Services Bottom Sheet
        if (showMoreMenuSheet) {
            ModalBottomSheet(
                onDismissRequest = { showMoreMenuSheet = false },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "GospelSphere Ecosystem Hub",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Access all platform features & services",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val ecosystemDestinations = listOf(
                        NavDestination.Churches,
                        NavDestination.Artists,
                        NavDestination.Marketplace,
                        NavDestination.AiAssistant,
                        NavDestination.Prayer,
                        NavDestination.Profile
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ecosystemDestinations.forEach { dest ->
                            ListItem(
                                headlineContent = { Text(dest.label, fontWeight = FontWeight.Bold) },
                                leadingContent = {
                                    Icon(
                                        dest.icon,
                                        contentDescription = dest.label,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                                    .testTag("sheet_menu_${dest.route}")
                                    .clickable {
                                        currentRoute = dest.route
                                        showMoreMenuSheet = false
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
