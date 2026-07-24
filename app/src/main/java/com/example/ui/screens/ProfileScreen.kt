package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.GospelSphereViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: GospelSphereViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()
    val favorites by viewModel.allFavorites.collectAsState(initial = emptyList())
    val receipt by viewModel.paymentReceipt.collectAsState()

    var showRoleSelector by remember { mutableStateOf(false) }
    val roles = listOf("Member", "Gospel Artist", "Church Administrator", "Ministry Leader", "Business Merchant")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .testTag("profile_screen_layout")
    ) {
        // User Banner Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = userProfile.name,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            if (userProfile.isVerified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.Verified, contentDescription = "Verified Account", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            }
                        }

                        Text(
                            text = userProfile.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        AssistChip(
                            onClick = { showRoleSelector = true },
                            label = { Text("Role: ${userProfile.role}") },
                            leadingIcon = { Icon(Icons.Default.Badge, contentDescription = "Role", modifier = Modifier.size(16.dp)) }
                        )
                    }
                }
            }
        }

        // Receipt Banner if giving or checkout was completed
        if (receipt != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ReceiptLong, contentDescription = "Receipt", tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Transaction Receipt", fontWeight = FontWeight.Bold)
                        }
                        IconButton(onClick = { viewModel.clearReceipt() }) {
                            Icon(Icons.Default.Close, contentDescription = "Dismiss")
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Ref: ${receipt!!.transactionId}", style = MaterialTheme.typography.labelMedium)
                    Text("Purpose: ${receipt!!.itemNameOrMinistry}", style = MaterialTheme.typography.bodyMedium)
                    Text("Amount: $${receipt!!.amountUsd} via ${receipt!!.paymentMethod}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Saved Favorites Section
        Text(
            text = "Saved Favorites (${favorites.size})",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved favorites yet. Tap the heart icon on any gospel track, sermon, or church to save them here.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(favorites) { fav ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                when (fav.mediaType) {
                                    "MUSIC" -> Icons.Default.MusicNote
                                    "VIDEO" -> Icons.Default.Videocam
                                    "CHURCH" -> Icons.Default.Church
                                    else -> Icons.Default.Star
                                },
                                contentDescription = fav.mediaType,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(fav.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                Text(fav.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            IconButton(onClick = {
                                viewModel.toggleFavorite(fav.mediaId, fav.mediaType, fav.title, fav.subtitle)
                            }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Remove", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }

    // Role Selection Dialog
    if (showRoleSelector) {
        AlertDialog(
            onDismissRequest = { showRoleSelector = false },
            title = { Text("Switch Ecosystem Role", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Select your primary account type in GospelSphere:", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))
                    roles.forEach { r ->
                        TextButton(
                            onClick = {
                                viewModel.updateUserRole(r)
                                showRoleSelector = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(r, fontWeight = if (userProfile.role == r) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRoleSelector = false }) {
                    Text("Close")
                }
            }
        )
    }
}
