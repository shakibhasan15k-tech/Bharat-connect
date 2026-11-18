package com.example.ui.call

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CallItem
import com.example.data.CallType
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun ActiveCallScreen(call: CallItem, viewModel: BharatConnectViewModel) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF0F172A)),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
          text = if (call.type == CallType.VIDEO || call.type == CallType.GROUP_VIDEO) "Bharat Connect HD Video Call" else "Bharat Connect HD Voice Call",
          color = GoldAccent,
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
          modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(EmeraldPrimary),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = call.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Encrypted • 02:45", color = Color.LightGray, fontSize = 14.sp)
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = {},
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
        ) {
          Icon(Icons.Default.Mic, contentDescription = "Mute", tint = Color.White)
        }

        IconButton(
          onClick = { viewModel.endCall() },
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color.Red)
            .testTag("end_call_button")
        ) {
          Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = Color.White)
        }

        IconButton(
          onClick = {},
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
        ) {
          Icon(Icons.Default.VolumeUp, contentDescription = "Speaker", tint = Color.White)
        }
      }
    }
  }
}
