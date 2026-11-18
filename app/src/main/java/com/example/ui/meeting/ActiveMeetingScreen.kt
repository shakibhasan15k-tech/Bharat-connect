package com.example.ui.meeting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.MeetingItem
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun ActiveMeetingScreen(meeting: MeetingItem, viewModel: BharatConnectViewModel) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF1E293B))
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = meeting.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
          Text(text = "Code: ${meeting.meetingCode}", color = GoldAccent, fontSize = 12.sp)
        }
        Button(
          onClick = { viewModel.leaveMeeting() },
          colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("leave_meeting_button")
        ) {
          Text("Leave", color = Color.White, fontWeight = FontWeight.Bold)
        }
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(vertical = 16.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(EmeraldPrimary),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(Icons.Default.Groups, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(64.dp))
          Spacer(modifier = Modifier.height(12.dp))
          Text(text = "Active Meeting Room", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
          Text(text = "${meeting.participantsCount} participants connected securely", color = Color.LightGray, fontSize = 14.sp)
        }
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
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
          onClick = {},
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
        ) {
          Icon(Icons.Default.Videocam, contentDescription = "Video", tint = Color.White)
        }
        IconButton(
          onClick = {},
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
        ) {
          Icon(Icons.Default.ScreenShare, contentDescription = "Share", tint = Color.White)
        }
        IconButton(
          onClick = {},
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
        ) {
          Icon(Icons.Default.Chat, contentDescription = "Chat", tint = Color.White)
        }
      }
    }
  }
}
