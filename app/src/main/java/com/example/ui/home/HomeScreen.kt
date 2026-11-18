package com.example.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: BharatConnectViewModel) {
  val selectedTab by viewModel.selectedTab.collectAsState()

  val tabs = listOf(
    TabItem("Chats", Icons.Filled.Chat, Icons.Outlined.Chat),
    TabItem("Updates", Icons.Filled.DonutLarge, Icons.Outlined.DonutLarge),
    TabItem("Calls", Icons.Filled.Call, Icons.Outlined.Call),
    TabItem("Meetings", Icons.Filled.Groups, Icons.Outlined.Groups),
    TabItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
  )

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Bharat Connect 🇮🇳") },
        actions = {
          IconButton(onClick = {}, modifier = Modifier.testTag("app_search_button")) {
            Icon(Icons.Default.Search, contentDescription = "Search")
          }
          IconButton(onClick = {}, modifier = Modifier.testTag("app_more_options_button")) {
            Icon(Icons.Default.MoreVert, contentDescription = "More Options")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = EmeraldPrimary,
          titleContentColor = Color.White
        )
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = EmeraldPrimary
      ) {
        tabs.forEachIndexed { index, tab ->
          val selected = selectedTab == index
          NavigationBarItem(
            selected = selected,
            onClick = { viewModel.setSelectedTab(index) },
            icon = {
              Icon(
                imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                contentDescription = tab.title
              )
            },
            label = { Text(tab.title) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = EmeraldPrimary,
              selectedTextColor = EmeraldPrimary,
              indicatorColor = GoldAccent.copy(alpha = 0.3f)
            ),
            modifier = Modifier.testTag("nav_tab_${tab.title.lowercase()}")
          )
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (selectedTab) {
        0 -> ChatsTab(viewModel)
        1 -> UpdatesTab(viewModel)
        2 -> CallsTab(viewModel)
        3 -> MeetingsTab(viewModel)
        4 -> ProfileSettingsTab(viewModel)
      }
    }
  }
}

data class TabItem(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector
)
