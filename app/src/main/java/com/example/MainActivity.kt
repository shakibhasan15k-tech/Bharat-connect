package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.call.ActiveCallScreen
import com.example.ui.chat.ChatDetailScreen
import com.example.ui.home.HomeScreen
import com.example.ui.meeting.ActiveMeetingScreen
import com.example.ui.onboarding.OnboardingScreen
import com.example.ui.security.AppLockScreen
import com.example.ui.theme.BharatConnectTheme
import com.example.viewmodel.BharatConnectViewModel
import com.example.viewmodel.OnboardingStep

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: BharatConnectViewModel = viewModel()
      val darkTheme by viewModel.darkTheme.collectAsState()
      val amoledMode by viewModel.amoledMode.collectAsState()
      val onboardingStep by viewModel.onboardingStep.collectAsState()
      val isLocked by viewModel.isLocked.collectAsState()
      val activeChatId by viewModel.activeChatId.collectAsState()
      val activeCall by viewModel.activeCall.collectAsState()
      val activeMeeting by viewModel.activeMeeting.collectAsState()
      val chats by viewModel.chats.collectAsState()

      BharatConnectTheme(darkTheme = darkTheme, amoledMode = amoledMode) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          when {
            isLocked -> AppLockScreen(viewModel)
            activeCall != null -> ActiveCallScreen(call = activeCall!!, viewModel = viewModel)
            activeMeeting != null -> ActiveMeetingScreen(meeting = activeMeeting!!, viewModel = viewModel)
            onboardingStep != OnboardingStep.HOME -> OnboardingScreen(viewModel)
            activeChatId != null -> {
              val chat = chats.find { it.id == activeChatId }
              if (chat != null) {
                ChatDetailScreen(chat = chat, viewModel = viewModel)
              } else {
                HomeScreen(viewModel)
              }
            }
            else -> HomeScreen(viewModel)
          }
        }
      }
    }
  }
}
