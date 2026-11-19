package com.example.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import com.example.data.Chat
import com.example.data.Message
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(chat: Chat, viewModel: BharatConnectViewModel) {
  val messagesMap by viewModel.messages.collectAsState()
  val messages = messagesMap[chat.id] ?: emptyList()
  var textInput by remember { mutableStateOf("") }
  var showAttachmentMenu by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(EmeraldPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (chat.isGroup) Icons.Default.Groups else Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(text = chat.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E7D32))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (chat.isGroup) "${chat.membersCount} members" else "online • End-to-end encrypted",
                  fontSize = 11.sp,
                  color = Color.Gray
                )
              }
            }
          }
        },
        navigationIcon = {
          IconButton(onClick = { viewModel.setActiveChat(null) }, modifier = Modifier.testTag("back_to_chats_button")) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          IconButton(
            onClick = {
              viewModel.startCall(
                com.example.data.CallItem(
                  id = "call_${System.currentTimeMillis()}",
                  name = chat.name,
                  avatarUrl = chat.avatarUrl,
                  type = com.example.data.CallType.VOICE,
                  isIncoming = false,
                  isMissed = false,
                  timestamp = "Just now",
                  duration = "0 min"
                )
              )
            },
            modifier = Modifier.testTag("start_voice_call_button")
          ) {
            Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = EmeraldPrimary)
          }
          IconButton(
            onClick = {
              viewModel.startCall(
                com.example.data.CallItem(
                  id = "call_${System.currentTimeMillis()}",
                  name = chat.name,
                  avatarUrl = chat.avatarUrl,
                  type = com.example.data.CallType.VIDEO,
                  isIncoming = false,
                  isMissed = false,
                  timestamp = "Just now",
                  duration = "0 min"
                )
              )
            },
            modifier = Modifier.testTag("start_video_call_button")
          ) {
            Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = EmeraldPrimary)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      Surface(
        tonalElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          if (showAttachmentMenu) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              AttachmentOption(icon = Icons.Default.Image, label = "Gallery") { showAttachmentMenu = false }
              AttachmentOption(icon = Icons.Default.Description, label = "Document") { showAttachmentMenu = false }
              AttachmentOption(icon = Icons.Default.LocationOn, label = "Location") { showAttachmentMenu = false }
              AttachmentOption(icon = Icons.Default.Headphones, label = "Audio") { showAttachmentMenu = false }
            }
          }

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(onClick = { showAttachmentMenu = !showAttachmentMenu }) {
              Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = EmeraldPrimary)
            }
            OutlinedTextField(
              value = textInput,
              onValueChange = { textInput = it },
              placeholder = { Text("Message Bharat Connect...") },
              modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
                .testTag("message_input"),
              shape = RoundedCornerShape(24.dp),
              maxLines = 4,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
              )
            )
            IconButton(
              onClick = {
                if (textInput.isNotBlank()) {
                  viewModel.sendMessage(chat.id, textInput)
                  textInput = ""
                }
              },
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(EmeraldPrimary)
                .testTag("send_message_button")
            ) {
              Icon(
                imageVector = if (textInput.isBlank()) Icons.Default.Mic else Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White
              )
            }
          }
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.background)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        reverseLayout = true
      ) {
        item {
          Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Lock, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Messages and calls are end-to-end encrypted. No one outside of this chat, not even Bharat Connect, can read or listen to them. 🇮🇳",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 14.sp
              )
            }
          }
        }

        items(messages.reversed()) { message ->
          MessageBubble(message = message)
        }
      }
    }
  }
}

@Composable
fun AttachmentOption(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.clickable(onClick = onClick)
  ) {
    Box(
      modifier = Modifier
        .size(52.dp)
        .clip(CircleShape)
        .background(EmeraldPrimary.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(icon, contentDescription = label, tint = EmeraldPrimary, modifier = Modifier.size(26.dp))
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
  }
}

@Composable
fun MessageBubble(message: Message) {
  val alignment = if (message.isMe) Alignment.End else Alignment.Start
  val bubbleColor = if (message.isMe) EmeraldPrimary else MaterialTheme.colorScheme.surfaceVariant
  val textColor = if (message.isMe) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = alignment
  ) {
    Surface(
      shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (message.isMe) 16.dp else 4.dp,
        bottomEnd = if (message.isMe) 4.dp else 16.dp
      ),
      color = bubbleColor,
      modifier = Modifier.widthIn(max = 280.dp)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        if (!message.isMe && message.senderName.isNotBlank()) {
          Text(text = message.senderName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
          Spacer(modifier = Modifier.height(2.dp))
        }
        Text(text = message.text, color = textColor, fontSize = 14.sp, lineHeight = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.align(Alignment.End),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = message.timestamp, fontSize = 10.sp, color = textColor.copy(alpha = 0.7f))
          if (message.isMe) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.DoneAll, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(12.dp))
          }
        }
      }
    }
  }
}
