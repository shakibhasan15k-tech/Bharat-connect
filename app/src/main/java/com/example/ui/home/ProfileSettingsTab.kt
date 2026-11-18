package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun ProfileSettingsTab(viewModel: BharatConnectViewModel) {
  val profile by viewModel.userProfile.collectAsState()
  val darkTheme by viewModel.darkTheme.collectAsState()
  val amoledMode by viewModel.amoledMode.collectAsState()
  val appLock by viewModel.appLockEnabled.collectAsState()
  var showSubScreen by remember { mutableStateOf<String?>(null) }

  Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(EmeraldPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
              Text(profile.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
              Text(profile.phoneNumber, fontSize = 13.sp, color = Color.Gray)
              Text("ID: ${profile.bharatId}", fontSize = 12.sp, color = GoldAccent)
            }
          }
        }
        Spacer(modifier = Modifier.height(8.dp))
      }

      item { SettingsCategoryHeader("Security & Privacy") }
      item {
        SettingsItem(
          icon = Icons.Default.Lock,
          title = "Privacy Controls",
          subtitle = "Last seen, online status, read receipts",
          onClick = { showSubScreen = "privacy" }
        )
      }
      item {
        SettingsItem(
          icon = Icons.Default.Fingerprint,
          title = "Passkeys & App Lock",
          subtitle = if (appLock) "Enabled (Biometric/PIN)" else "Disabled",
          onClick = { viewModel.toggleAppLock(!appLock) }
        )
      }

      item { SettingsCategoryHeader("Appearance & Customization") }
      item {
        SettingsToggleItem(
          icon = Icons.Default.DarkMode,
          title = "Dark Mode",
          subtitle = "Enable dark interface",
          checked = darkTheme,
          onCheckedChange = { viewModel.toggleDarkTheme(it) }
        )
      }
      item {
        SettingsToggleItem(
          icon = Icons.Default.Contrast,
          title = "AMOLED Black Mode",
          subtitle = "Pure black for battery saving",
          checked = amoledMode,
          onCheckedChange = { viewModel.toggleAmoledMode(it) }
        )
      }

      item { SettingsCategoryHeader("Data & Storage") }
      item {
        SettingsItem(
          icon = Icons.Default.Storage,
          title = "Storage and Data Manager",
          subtitle = "Manage network usage, auto-download",
          onClick = { showSubScreen = "storage" }
        )
      }
      item {
        SettingsItem(
          icon = Icons.Default.Devices,
          title = "Linked Devices",
          subtitle = "Manage Web & Desktop sessions",
          onClick = { showSubScreen = "devices" }
        )
      }

      item { SettingsCategoryHeader("Backup & Support") }
      item {
        SettingsItem(
          icon = Icons.Default.CloudUpload,
          title = "Encrypted Backup & Restore",
          subtitle = "Secure local and cloud backups",
          onClick = { showSubScreen = "backup" }
        )
      }
      item {
        SettingsItem(
          icon = Icons.Default.Help,
          title = "Help & Support",
          subtitle = "FAQs, contact support, report issue",
          onClick = { showSubScreen = "help" }
        )
      }
    }

    if (showSubScreen != null) {
      SettingsSubScreenModal(title = showSubScreen!!.uppercase(), onDismiss = { showSubScreen = null })
    }
  }
}

@Composable
fun SettingsCategoryHeader(title: String) {
  Text(
    text = title,
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold,
    color = EmeraldPrimary,
    modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 4.dp)
  )
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(icon, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(24.dp))
      Spacer(modifier = Modifier.width(16.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(subtitle, fontSize = 12.sp, color = Color.Gray)
      }
      Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
  }
}

@Composable
fun SettingsToggleItem(icon: ImageVector, title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(icon, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(24.dp))
      Spacer(modifier = Modifier.width(16.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(subtitle, fontSize = 12.sp, color = Color.Gray)
      }
      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(checkedThumbColor = GoldAccent, checkedTrackColor = EmeraldPrimary)
      )
    }
  }
}

@Composable
fun SettingsSubScreenModal(title: String, onDismiss: () -> Unit) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Bharat Connect $title") },
    text = {
      Text("Advanced configuration options and secure encryption protocols are active for $title.")
    },
    confirmButton = {
      Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
        Text("Done")
      }
    }
  )
}
