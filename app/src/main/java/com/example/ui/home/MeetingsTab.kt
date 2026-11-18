package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun MeetingsTab(viewModel: BharatConnectViewModel) {
  val meetings by viewModel.meetings.collectAsState()
  var showScheduleDialog by remember { mutableStateOf(false) }

  Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("Bharat Meetings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
          Button(
            onClick = {
              viewModel.joinMeeting(
                MeetingItem(
                  id = "instant_${System.currentTimeMillis()}",
                  title = "Instant Secure Meeting",
                  hostName = "Aarav Sharma",
                  meetingCode = "IND-999-000",
                  password = "bharat",
                  scheduledTime = "Right Now",
                  isInstant = true,
                  participantsCount = 1
                )
              )
            },
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("instant_meeting_button")
          ) {
            Icon(Icons.Default.VideoCall, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text("New Instant", color = Color.Black, fontWeight = FontWeight.Bold)
          }
        }
        Spacer(modifier = Modifier.height(12.dp))
      }

      items(meetings) { meeting ->
        MeetingItemCard(meeting = meeting, onJoin = { viewModel.joinMeeting(meeting) })
      }
    }

    FloatingActionButton(
      onClick = { showScheduleDialog = true },
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(24.dp)
        .testTag("schedule_meeting_fab"),
      containerColor = EmeraldPrimary,
      contentColor = Color.White
    ) {
      Icon(Icons.Default.Add, contentDescription = "Schedule Meeting")
    }

    if (showScheduleDialog) {
      ScheduleMeetingDialog(
        onDismiss = { showScheduleDialog = false },
        onSchedule = { title, time ->
          viewModel.scheduleMeeting(title, time)
          showScheduleDialog = false
        }
      )
    }
  }
}

@Composable
fun MeetingItemCard(meeting: MeetingItem, onJoin: () -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(meeting.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Badge(containerColor = EmeraldPrimary, contentColor = Color.White) {
          Text(meeting.scheduledTime, modifier = Modifier.padding(6.dp, 2.dp))
        }
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text("Meeting Code: ${meeting.meetingCode}", fontSize = 13.sp, color = Color.Gray)
      Text("Password: ${meeting.password}", fontSize = 13.sp, color = Color.Gray)
      Spacer(modifier = Modifier.height(12.dp))
      Button(
        onClick = onJoin,
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().testTag("join_meeting_${meeting.id}")
      ) {
        Text("Join Meeting Securely")
      }
    }
  }
}

@Composable
fun ScheduleMeetingDialog(onDismiss: () -> Unit, onSchedule: (String, String) -> Unit) {
  var title by remember { mutableStateOf("") }
  var time by remember { mutableStateOf("Tomorrow, 10:00 AM") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Schedule Meeting") },
    text = {
      Column {
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("Meeting Title") },
          modifier = Modifier.fillMaxWidth().testTag("meeting_title_input"),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
          value = time,
          onValueChange = { time = it },
          label = { Text("Time / Date") },
          modifier = Modifier.fillMaxWidth().testTag("meeting_time_input"),
          singleLine = true
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { if (title.isNotBlank()) onSchedule(title, time) },
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        modifier = Modifier.testTag("confirm_schedule_button")
      ) {
        Text("Schedule")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
