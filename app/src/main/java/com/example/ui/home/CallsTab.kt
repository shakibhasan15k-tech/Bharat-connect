package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.example.data.CallItem
import com.example.data.CallType
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun CallsTab(viewModel: BharatConnectViewModel) {
  val calls by viewModel.calls.collectAsState()

  Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        Text("Recent HD Calls", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
      }

      items(calls) { call ->
        CallItemCard(call = call, onCallClick = { viewModel.startCall(call) })
      }
    }

    FloatingActionButton(
      onClick = {
        viewModel.startCall(
          CallItem(
            id = "call_new_${System.currentTimeMillis()}",
            name = "Priya Patel",
            avatarUrl = "",
            type = CallType.VIDEO,
            isIncoming = false,
            isMissed = false,
            timestamp = "Just now",
            duration = "0 min"
          )
        )
      },
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(24.dp)
        .testTag("new_call_fab"),
      containerColor = EmeraldPrimary,
      contentColor = Color.White
    ) {
      Icon(Icons.Default.VideoCall, contentDescription = "New Call")
    }
  }
}

@Composable
fun CallItemCard(call: CallItem, onCallClick: () -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(EmeraldPrimary),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (call.type == CallType.VIDEO || call.type == CallType.GROUP_VIDEO) Icons.Default.Videocam else Icons.Default.Call,
            contentDescription = null,
            tint = Color.White
          )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(call.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (call.isMissed) Icons.Default.CallMissed else Icons.Default.CallReceived,
              contentDescription = null,
              tint = if (call.isMissed) Color.Red else EmeraldPrimary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(call.timestamp, fontSize = 12.sp, color = Color.Gray)
          }
        }
      }

      IconButton(
        onClick = onCallClick,
        modifier = Modifier.testTag("call_action_${call.id}")
      ) {
        Icon(
          imageVector = if (call.type == CallType.VIDEO || call.type == CallType.GROUP_VIDEO) Icons.Default.Videocam else Icons.Default.Call,
          contentDescription = "Call",
          tint = EmeraldPrimary
        )
      }
    }
  }
}
