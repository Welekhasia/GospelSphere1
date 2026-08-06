package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.GospelSphereViewModel
import com.example.ui.theme.*

// Sidebar Navigation Items
enum class AdminSidebarSection(val label: String, val icon: ImageVector, val badge: String? = null) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    MUSIC("Music", Icons.Default.MusicNote, "5"),
    VIDEOS("Videos", Icons.Default.VideoLibrary),
    SERMONS("Sermons", Icons.Default.Mic),
    BIBLE("Bible", Icons.AutoMirrored.Filled.MenuBook),
    CHURCHES("Churches", Icons.Default.Church, "3"),
    ARTISTS("Artists", Icons.Default.Person),
    PRAYERS("Prayer Requests", Icons.Default.VolunteerActivism, "12"),
    EVENTS("Events", Icons.Default.Event),
    MARKETPLACE("Marketplace", Icons.Default.Storefront),
    DONATIONS("Donations", Icons.Default.Favorite),
    ANALYTICS("Analytics", Icons.Default.ShowChart),
    USERS("Users", Icons.Default.People, "18"),
    SETTINGS("Settings", Icons.Default.Settings),
    LOGOUT("Logout", Icons.AutoMirrored.Filled.ExitToApp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: GospelSphereViewModel,
    onNavigateBackToApp: () -> Unit = {}
) {
    var selectedSection by remember { mutableStateOf(AdminSidebarSection.DASHBOARD) }
    var isSidebarExpanded by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var isDarkMode by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showMessagesDialog by remember { mutableStateOf(false) }
    var showAdminProfileMenu by remember { mutableStateOf(false) }
    var showQuickActionSheet by remember { mutableStateOf(false) }
    var quickActionTitle by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val bgBackgroundColor = if (isDarkMode) BgDark else BgLight
    val cardBgColor = if (isDarkMode) CardDark else CardLight
    val textColor = if (isDarkMode) TextLight else TextDark

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBackgroundColor)
            .testTag("admin_dashboard_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ================= 1. HEADER BAR =================
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp),
                color = NavyPrimary,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Logo & Toggle Sidebar Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        IconButton(
                            onClick = { isSidebarExpanded = !isSidebarExpanded },
                            modifier = Modifier.testTag("toggle_sidebar_button")
                        ) {
                            Icon(
                                imageVector = if (isSidebarExpanded) Icons.Default.MenuOpen else Icons.Default.Menu,
                                contentDescription = "Toggle Sidebar",
                                tint = GospelGold
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.img_gospelsphere_logo_1785444018542),
                            contentDescription = "GospelSphere Logo",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            Text(
                                text = "GospelSphere",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Admin Portal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GospelGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    // Global Search Input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text("Global Search...", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = GospelGold, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.White)
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavySecondary,
                            unfocusedContainerColor = NavySecondary.copy(alpha = 0.7f),
                            focusedBorderColor = GospelGold,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .widthIn(max = 280.dp)
                            .height(46.dp)
                            .testTag("admin_global_search_input"),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                    )

                    // Header Right Actions (Notifications, Messages, Dark Mode, Profile)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Notifications
                        BadgedBox(
                            badge = {
                                Badge(containerColor = StatusDanger) {
                                    Text("4", color = Color.White, fontSize = 10.sp)
                                }
                            }
                        ) {
                            IconButton(
                                onClick = { showNotificationsDialog = true },
                                modifier = Modifier.testTag("notifications_button")
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                            }
                        }

                        // Messages
                        BadgedBox(
                            badge = {
                                Badge(containerColor = GospelGold) {
                                    Text("2", color = NavyPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        ) {
                            IconButton(
                                onClick = { showMessagesDialog = true },
                                modifier = Modifier.testTag("messages_button")
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Comment, contentDescription = "Messages", tint = Color.White)
                            }
                        }

                        // Dark Mode Switch
                        IconButton(
                            onClick = { isDarkMode = !isDarkMode },
                            modifier = Modifier.testTag("dark_mode_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme",
                                tint = GospelGold
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // Admin Profile Button & Menu
                        Box {
                            Surface(
                                modifier = Modifier
                                    .clickable { showAdminProfileMenu = true }
                                    .testTag("admin_profile_button"),
                                shape = RoundedCornerShape(20.dp),
                                color = NavySecondary
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(GospelGold),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("AW", fontWeight = FontWeight.Bold, color = NavyPrimary, fontSize = 12.sp)
                                    }
                                    Text(
                                        text = "Ali Welekhasia",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                    )
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Menu", tint = GospelGold)
                                }
                            }

                            DropdownMenu(
                                expanded = showAdminProfileMenu,
                                onDismissRequest = { showAdminProfileMenu = false },
                                modifier = Modifier
                                    .background(NavySecondary)
                                    .border(1.dp, GospelGold, RoundedCornerShape(8.dp))
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Profile Settings", color = Color.White) },
                                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = GospelGold) },
                                    onClick = {
                                        showAdminProfileMenu = false
                                        selectedSection = AdminSidebarSection.SETTINGS
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Role Permissions", color = Color.White) },
                                    leadingIcon = { Icon(Icons.Default.Security, contentDescription = null, tint = GospelGold) },
                                    onClick = {
                                        showAdminProfileMenu = false
                                        selectedSection = AdminSidebarSection.USERS
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Backup & Restore", color = Color.White) },
                                    leadingIcon = { Icon(Icons.Default.Backup, contentDescription = null, tint = GospelGold) },
                                    onClick = {
                                        showAdminProfileMenu = false
                                        selectedSection = AdminSidebarSection.SETTINGS
                                    }
                                )
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                DropdownMenuItem(
                                    text = { Text("Return to App", color = GospelGold, fontWeight = FontWeight.Bold) },
                                    leadingIcon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = GospelGold) },
                                    onClick = {
                                        showAdminProfileMenu = false
                                        onNavigateBackToApp()
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ================= 2. MAIN BODY (SIDEBAR + CONTENT) =================
            Row(modifier = Modifier.fillMaxSize()) {
                // Collapsible Sidebar
                AnimatedVisibility(
                    visible = isSidebarExpanded,
                    enter = expandHorizontally() + fadeIn(),
                    exit = shrinkHorizontally() + fadeOut()
                ) {
                    Surface(
                        modifier = Modifier
                            .width(220.dp)
                            .fillMaxHeight(),
                        color = NavyPrimary,
                        tonalElevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 12.dp, horizontal = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "MAIN NAVIGATION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GospelGold.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )

                            AdminSidebarSection.values().forEach { section ->
                                val isSelected = selectedSection == section
                                Surface(
                                    onClick = {
                                        if (section == AdminSidebarSection.LOGOUT) {
                                            onNavigateBackToApp()
                                        } else {
                                            selectedSection = section
                                        }
                                    },
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) GospelGold else Color.Transparent,
                                    contentColor = if (isSelected) NavyPrimary else Color.White,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("admin_sidebar_item_${section.name.lowercase()}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Icon(
                                                imageVector = section.icon,
                                                contentDescription = section.label,
                                                tint = if (isSelected) NavyPrimary else GospelGold,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = section.label,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                                ),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        if (section.badge != null) {
                                            Surface(
                                                shape = RoundedCornerShape(10.dp),
                                                color = if (isSelected) NavyPrimary else GospelGold
                                            ) {
                                                Text(
                                                    text = section.badge,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 10.sp
                                                    ),
                                                    color = if (isSelected) GospelGold else NavyPrimary,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Content View Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    when (selectedSection) {
                        AdminSidebarSection.DASHBOARD -> AdminDashboardHomeView(
                            isDarkMode = isDarkMode,
                            onQuickAction = { actionName ->
                                quickActionTitle = actionName
                                showQuickActionSheet = true
                            },
                            onNavigateSection = { selectedSection = it }
                        )
                        AdminSidebarSection.MUSIC -> AdminMusicModuleView(cardBgColor, textColor)
                        AdminSidebarSection.VIDEOS -> AdminGenericModuleView("Videos Management", Icons.Default.VideoLibrary, "Manage GospelSphere Video Sermons and Music Clips", cardBgColor, textColor)
                        AdminSidebarSection.SERMONS -> AdminGenericModuleView("Sermons & Preaching", Icons.Default.Mic, "Upload and verify church sermons & podcasts", cardBgColor, textColor)
                        AdminSidebarSection.BIBLE -> AdminGenericModuleView("Bible Study Module", Icons.AutoMirrored.Filled.MenuBook, "Manage Scripture Translations, Devotionals, and Bookmarks", cardBgColor, textColor)
                        AdminSidebarSection.CHURCHES -> AdminChurchModuleView(cardBgColor, textColor)
                        AdminSidebarSection.ARTISTS -> AdminGenericModuleView("Gospel Artists Verification", Icons.Default.Person, "Verify Gospel Artists and manage profile status", cardBgColor, textColor)
                        AdminSidebarSection.PRAYERS -> AdminPrayerModuleView(cardBgColor, textColor)
                        AdminSidebarSection.EVENTS -> AdminGenericModuleView("Church Events Calendar", Icons.Default.Event, "Manage Christian Conferences, Live Streams and Local Services", cardBgColor, textColor)
                        AdminSidebarSection.MARKETPLACE -> AdminGenericModuleView("Christian Marketplace & Store", Icons.Default.Storefront, "Review Bibles, Christian Literature, Merch & Digital Orders", cardBgColor, textColor)
                        AdminSidebarSection.DONATIONS -> AdminGenericModuleView("Donations & Tithing Portal", Icons.Default.Favorite, "Track Ministry Giving, Church Tithes & Mission Support", cardBgColor, textColor)
                        AdminSidebarSection.ANALYTICS -> AdminAnalyticsModuleView(cardBgColor, textColor)
                        AdminSidebarSection.USERS -> AdminUsersModuleView(cardBgColor, textColor)
                        AdminSidebarSection.SETTINGS -> AdminSettingsModuleView(isDarkMode, onToggleDarkMode = { isDarkMode = !isDarkMode }, cardBgColor, textColor)
                        AdminSidebarSection.LOGOUT -> {
                            LaunchedEffect(Unit) { onNavigateBackToApp() }
                        }
                    }
                }
            }
        }

        // ================= 3. DIALOGS & QUICK ACTION MODAL =================
        if (showNotificationsDialog) {
            AlertDialog(
                onDismissRequest = { showNotificationsDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = GospelGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Admin Notifications", fontWeight = FontWeight.Bold)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        NotificationItem("New Church Pending Approval", "Three Rivers Of Grace Fellowship requested verification", "10 mins ago", StatusWarning)
                        NotificationItem("Artist Profile Submitted", "Ali Welekhasia uploaded new song 'LAMWELI'", "1 hr ago", StatusSuccess)
                        NotificationItem("High Prayer Activity", "12 new prayer requests submitted today", "3 hrs ago", NavySecondary)
                        NotificationItem("System Backup Completed", "Database backup saved successfully", "5 hrs ago", StatusSuccess)
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showNotificationsDialog = false }) {
                        Text("Close", color = GospelGold, fontWeight = FontWeight.Bold)
                    }
                },
                containerColor = cardBgColor,
                titleContentColor = textColor,
                textContentColor = textColor
            )
        }

        if (showMessagesDialog) {
            AlertDialog(
                onDismissRequest = { showMessagesDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.Comment, contentDescription = null, tint = GospelGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Admin Support Messages", fontWeight = FontWeight.Bold)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        MessageItem("Pastor Aliwa Richard", "Thank you for approving Vosh Church Satellite page!", "Today 09:15 AM")
                        MessageItem("Esther (Member)", "Can I update my prayer request category?", "Yesterday")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showMessagesDialog = false }) {
                        Text("Close", color = GospelGold, fontWeight = FontWeight.Bold)
                    }
                },
                containerColor = cardBgColor,
                titleContentColor = textColor,
                textContentColor = textColor
            )
        }

        if (showQuickActionSheet) {
            AlertDialog(
                onDismissRequest = { showQuickActionSheet = false },
                title = { Text(quickActionTitle, fontWeight = FontWeight.Bold, color = GospelGold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Enter the details for this admin quick action. Changes will immediately sync across GospelSphere.", style = MaterialTheme.typography.bodyMedium)
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Title / Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Description / Notes") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showQuickActionSheet = false },
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                    ) {
                        Text("Save & Publish", color = GospelGold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showQuickActionSheet = false }) {
                        Text("Cancel")
                    }
                },
                containerColor = cardBgColor,
                titleContentColor = textColor,
                textContentColor = textColor
            )
        }
    }
}

// ================= MAIN DASHBOARD HOME VIEW =================
@Composable
fun AdminDashboardHomeView(
    isDarkMode: Boolean,
    onQuickAction: (String) -> Unit,
    onNavigateSection: (AdminSidebarSection) -> Unit
) {
    val cardBgColor = if (isDarkMode) CardDark else CardLight
    val textColor = if (isDarkMode) TextLight else TextDark

    var selectedChartMetric by remember { mutableStateOf("Streams") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step 4: Welcome Section & Daily Metrics Checklist
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("welcome_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NavyPrimary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(NavyPrimary, NavySecondary)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Welcome back,",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "Ali Welekhasia",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GospelGold
                                    )
                                )
                                Text(
                                    text = "Manage GospelSphere from one place.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = GospelGold
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Verified, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Super Admin", fontWeight = FontWeight.Bold, color = NavyPrimary, fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "TODAY'S HIGHLIGHTS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = GospelGold,
                                letterSpacing = 1.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            item { DailyHighlightBadge("✔ 18 New Members", StatusSuccess) }
                            item { DailyHighlightBadge("✔ 5 New Songs", GospelGold) }
                            item { DailyHighlightBadge("✔ 3 New Churches", StatusWarning) }
                            item { DailyHighlightBadge("✔ 12 Prayer Requests", Color(0xFF64B5F6)) }
                        }
                    }
                }
            }
        }

        // Quick Actions Row
        item {
            Column {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.testTag("quick_actions_row")
                ) {
                    item { QuickActionButton("➕ Add Song", Icons.Default.MusicNote) { onQuickAction("Add New Gospel Song") } }
                    item { QuickActionButton("➕ Add Video", Icons.Default.VideoLibrary) { onQuickAction("Add Video Sermon / Clip") } }
                    item { QuickActionButton("➕ Register Church", Icons.Default.Church) { onQuickAction("Register Church Directory") } }
                    item { QuickActionButton("➕ Add Artist", Icons.Default.Person) { onQuickAction("Add Gospel Artist Profile") } }
                    item { QuickActionButton("➕ Publish Sermon", Icons.Default.Mic) { onQuickAction("Publish New Sermon") } }
                    item { QuickActionButton("➕ Create Event", Icons.Default.Event) { onQuickAction("Create Christian Event") } }
                    item { QuickActionButton("➕ Marketplace Product", Icons.Default.Storefront) { onQuickAction("Add Marketplace Item") } }
                    item { QuickActionButton("➕ Send Announcement", Icons.Default.Campaign) { onQuickAction("Send Global Announcement") } }
                }
            }
        }

        // Step 5: Statistics Cards (8 Summary Cards)
        item {
            Text(
                text = "Platform Statistics",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Members",
                        value = "0",
                        change = "▲ -3000%",
                        isPositive = false,
                        icon = Icons.Default.People,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Songs",
                        value = "6",
                        change = "▲ +20%",
                        isPositive = true,
                        icon = Icons.Default.MusicNote,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Videos",
                        value = "0",
                        change = "▲ -3000%",
                        isPositive = false,
                        icon = Icons.Default.VideoLibrary,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Churches",
                        value = "1",
                        change = "▲ +3%",
                        isPositive = true,
                        icon = Icons.Default.Church,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Artists",
                        value = "2",
                        change = "▲ +9%",
                        isPositive = true,
                        icon = Icons.Default.Person,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Prayers",
                        value = "79",
                        change = "▼ -5%",
                        isPositive = false,
                        icon = Icons.Default.VolunteerActivism,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Donations",
                        value = "KES 0",
                        change = "▲ +0%",
                        isPositive = true,
                        icon = Icons.Default.Favorite,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Marketplace",
                        value = "0 Orders",
                        change = "▲ -1%",
                        isPositive = false,
                        icon = Icons.Default.Storefront,
                        cardBgColor = cardBgColor,
                        textColor = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Step 6: Interactive Analytics Chart
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("analytics_chart_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Analytics & Growth Trends", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = textColor))
                            Text("Real-time telemetry across GospelSphere", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }

                        Icon(Icons.Default.ShowChart, contentDescription = null, tint = GospelGold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Metric Selectors
                    val chartMetrics = listOf("Streams", "Visitors", "Donations", "User Growth", "New Churches", "Prayer Requests", "Marketplace")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(chartMetrics) { metric ->
                            FilterChip(
                                selected = selectedChartMetric == metric,
                                onClick = { selectedChartMetric = metric },
                                label = { Text(metric, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NavyPrimary,
                                    selectedLabelColor = GospelGold,
                                    containerColor = cardBgColor,
                                    labelColor = textColor
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Custom Compose Canvas Chart Render
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(8.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val points = listOf(15f, 30f, 25f, 60f, 45f, 85f, 95f)
                            val width = size.width
                            val height = size.height
                            val spacing = width / (points.size - 1)

                            val path = Path().apply {
                                points.forEachIndexed { i, pt ->
                                    val x = i * spacing
                                    val y = height - (pt / 100f * height)
                                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                                }
                            }

                            // Draw gradient background under line chart
                            val fillPath = Path().apply {
                                addPath(path)
                                lineTo(width, height)
                                lineTo(0f, height)
                                close()
                            }

                            drawPath(
                                path = fillPath,
                                brush = Brush.verticalGradient(
                                    colors = listOf(GospelGold.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )

                            // Draw primary line
                            drawPath(
                                path = path,
                                color = GospelGold,
                                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                            )

                            // Draw data node points
                            points.forEachIndexed { i, pt ->
                                val x = i * spacing
                                val y = height - (pt / 100f * height)
                                drawCircle(color = NavyPrimary, radius = 5.dp.toPx(), center = Offset(x, y))
                                drawCircle(color = GospelGold, radius = 3.dp.toPx(), center = Offset(x, y))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul").forEach { month ->
                            Text(month, fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }
            }
        }

        // Step 7: Recent Activity Tables (Recent Songs, Recent Churches, Recent Prayer Requests)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Recent Activity Tables",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )

                // Recent Songs Table Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Latest Songs", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = textColor))
                            TextButton(onClick = { onNavigateSection(AdminSidebarSection.MUSIC) }) {
                                Text("View All", color = GospelGold)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Table Header
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Text("Song", modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Artist", modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Status", modifier = Modifier.weight(0.8f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                        }

                        TableRowSong("LAMWELI", "Ali Welekhasia", "Published", StatusSuccess)
                        TableRowSong("NI WEWE", "Ali Welekhasia", "Live", StatusSuccess)
                        TableRowSong("ZAWADI", "Ali Welekhasia", "Pending", StatusWarning)
                    }
                }

                // Recent Churches Table Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Latest Churches", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = textColor))
                            TextButton(onClick = { onNavigateSection(AdminSidebarSection.CHURCHES) }) {
                                Text("View All", color = GospelGold)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Table Header
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Text("Church", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Pastor", modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Members", modifier = Modifier.weight(0.8f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                        }

                        TableRowChurch("Three Rivers Of Grace Fellowship", "Bsp. FELIX BURUDI ATILA", "324")
                        TableRowChurch("Vosh church satellite", "Pastor Aliwa Richard", "188")
                    }
                }

                // Recent Prayer Requests Table Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBgColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Latest Prayer Requests", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = textColor))
                            TextButton(onClick = { onNavigateSection(AdminSidebarSection.PRAYERS) }) {
                                Text("View All", color = GospelGold)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Table Header
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Text("Name", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Category", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                            Text("Status", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextMuted)
                        }

                        TableRowPrayer("James", "Healing", "Open", StatusWarning)
                        TableRowPrayer("Esther", "Family", "Answered", StatusSuccess)
                    }
                }
            }
        }
    }
}

// ================= COMPONENT HELPER FUNCTIONS =================
@Composable
fun DailyHighlightBadge(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.2f),
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun QuickActionButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = NavyPrimary,
        border = BorderStroke(1.dp, GospelGold.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(icon, contentDescription = null, tint = GospelGold, modifier = Modifier.size(16.dp))
            Text(title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = Color.White))
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    icon: ImageVector,
    cardBgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.testTag("stat_card_${title.lowercase()}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(NavyPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = title, tint = GospelGold, modifier = Modifier.size(20.dp))
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isPositive) StatusSuccess.copy(alpha = 0.15f) else StatusDanger.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = change,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isPositive) StatusSuccess else StatusDanger,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )
        }
    }
}

@Composable
fun TableRowSong(title: String, artist: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1.2f), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        Text(artist, modifier = Modifier.weight(1.2f), fontSize = 13.sp, color = TextMuted)
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = statusColor.copy(alpha = 0.15f),
            modifier = Modifier.weight(0.8f)
        ) {
            Text(
                text = status,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun TableRowChurch(name: String, pastor: String, members: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.weight(1.5f), fontWeight = FontWeight.SemiBold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(pastor, modifier = Modifier.weight(1.2f), fontSize = 13.sp, color = TextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(members, modifier = Modifier.weight(0.8f), fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TableRowPrayer(name: String, category: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        Text(category, modifier = Modifier.weight(1f), fontSize = 13.sp, color = TextMuted)
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = statusColor.copy(alpha = 0.15f),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = status,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun NotificationItem(title: String, desc: String, time: String, iconColor: Color) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(iconColor)
        )
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(desc, fontSize = 12.sp, color = TextMuted)
            Text(time, fontSize = 10.sp, color = GospelGold)
        }
    }
}

@Composable
fun MessageItem(sender: String, message: String, time: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(sender, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = GospelGold)
            Text(time, fontSize = 10.sp, color = TextMuted)
        }
        Text(message, fontSize = 12.sp)
    }
}

// ================= SPECIFIC MODULE VIEWS =================
@Composable
fun AdminMusicModuleView(cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Music Management Module", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
                    Text("Review, approve and manage Gospel songs across all artists", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = GospelGold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Upload Song", color = GospelGold)
                }
            }
        }

        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Songs Library (6 Active)", fontWeight = FontWeight.Bold)
                    TableRowSong("LAMWELI", "Ali Welekhasia", "Published", StatusSuccess)
                    TableRowSong("NI WEWE", "Ali Welekhasia", "Live", StatusSuccess)
                    TableRowSong("ZAWADI", "Ali Welekhasia", "Pending Review", StatusWarning)
                    TableRowSong("VICTORY BELONGS TO JESUS", "Todd Dulaney", "Published", StatusSuccess)
                    TableRowSong("WAY MAKER", "Sinach", "Published", StatusSuccess)
                    TableRowSong("YOU SAY", "Lauren Daigle", "Published", StatusSuccess)
                }
            }
        }
    }
}

@Composable
fun AdminChurchModuleView(cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Church Directory Management", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
            Text("Approve registered churches, pastor links & service schedules", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }

        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TableRowChurch("Three Rivers Of Grace Fellowship", "Bsp. FELIX BURUDI ATILA", "324 Members")
                    TableRowChurch("Vosh church satellite", "Pastor Aliwa Richard", "188 Members")
                }
            }
        }
    }
}

@Composable
fun AdminPrayerModuleView(cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Prayer Requests Management", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
            Text("Moderate prayer walls, categorize requests and respond to believers", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }

        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TableRowPrayer("James", "Healing & Recovery", "Open", StatusWarning)
                    TableRowPrayer("Esther", "Family Restoration", "Answered", StatusSuccess)
                    TableRowPrayer("Bsp. Felix", "Church Building Grace", "Active", NavySecondary)
                }
            }
        }
    }
}

@Composable
fun AdminAnalyticsModuleView(cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Full Analytics & Platform Telemetry", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
        }
        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Platform Monthly Visitors: 142,500", fontWeight = FontWeight.Bold)
                    Text("Total Audio Streams: 89,320 plays", fontWeight = FontWeight.Medium, color = GospelGold)
                    Text("Total Video Sermon Views: 34,100 views", fontWeight = FontWeight.Medium, color = StatusSuccess)
                }
            }
        }
    }
}

@Composable
fun AdminUsersModuleView(cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("User Management & Role Permissions", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
        }
        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("1. Ali Welekhasia — Super Administrator", fontWeight = FontWeight.Bold)
                    Text("2. Bsp. FELIX BURUDI ATILA — Pastor / Verified Church Lead", fontSize = 13.sp)
                    Text("3. Pastor Aliwa Richard — Pastor / Satellite Leader", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun AdminSettingsModuleView(isDarkMode: Boolean, onToggleDarkMode: () -> Unit, cardBgColor: Color, textColor: Color) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Admin Settings & Maintenance", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
        }

        item {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Dark Mode Interface", fontWeight = FontWeight.Bold)
                        Switch(checked = isDarkMode, onCheckedChange = { onToggleDarkMode() })
                    }

                    HorizontalDivider()

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                    ) {
                        Icon(Icons.Default.Backup, contentDescription = null, tint = GospelGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Backup & Restore System Data", color = GospelGold)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminGenericModuleView(title: String, icon: ImageVector, subtitle: String, cardBgColor: Color, textColor: Color) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = cardBgColor)) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = GospelGold, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = textColor))
            }
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
                Text("Manage $title", color = GospelGold)
            }
        }
    }
}
