package com.example.ui.home

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Chat
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun ChatsTab(viewModel: BharatConnectViewModel) {
  val chats by viewModel.chats.collectAsState()
  var searchQuery by remember { mutableStateOf("") }
  var showMenu by remember { mutableStateOf(false) }
  var showNewGroupDialog by remember { mutableStateOf(false) }
  var showNewContactDialog by remember { mutableStateOf(false) }

  val filteredChats = chats.filter { it.name.contains(searchQuery, ignoreCase = true) || it.lastMessage.contains(searchQuery, ignoreCase = true) }

  Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search chats, messages...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
        modifier = Modifier.fillMaxWidth().testTag("search_chats_input"),
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = EmeraldPrimary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(12.dp))

      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(filteredChats) { chat ->
          ChatItemCard(chat = chat, onClick = { viewModel.setActiveChat(chat.id) })
        }
      }
    }

    Box(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(24.dp)
    ) {
      Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (showMenu) {
          ExtendedFloatingActionButton(
            onClick = {
              showMenu = false
              showNewContactDialog = true
            },
            containerColor = GoldAccent,
            contentColor = Color.Black,
            modifier = Modifier.testTag("add_contact_fab_option")
          ) {
            Icon(Icons.Default.PersonAdd, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add New Contact")
          }

          ExtendedFloatingActionButton(
            onClick = {
              showMenu = false
              showNewGroupDialog = true
            },
            containerColor = EmeraldPrimary,
            contentColor = Color.White,
            modifier = Modifier.testTag("add_group_fab_option")
          ) {
            Icon(Icons.Default.GroupAdd, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create New Group")
          }
        }

        FloatingActionButton(
          onClick = { showMenu = !showMenu },
          modifier = Modifier.testTag("main_add_fab"),
          containerColor = EmeraldPrimary,
          contentColor = Color.White
        ) {
          Icon(
            imageVector = if (showMenu) Icons.Default.Close else Icons.Default.Add,
            contentDescription = "Add"
          )
        }
      }
    }

    if (showNewGroupDialog) {
      NewGroupDialog(
        onDismiss = { showNewGroupDialog = false },
        onCreate = { name, desc ->
          viewModel.createGroup(name, desc)
          showNewGroupDialog = false
        }
      )
    }

    if (showNewContactDialog) {
      NewContactDialog(
        onDismiss = { showNewContactDialog = false },
        onAdd = { name, phone, about ->
          viewModel.addContact(name, phone, about)
          showNewContactDialog = false
        }
      )
    }
  }
}

@Composable
fun ChatItemCard(chat: Chat, onClick: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("chat_item_${chat.id}"),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(50.dp)
          .clip(CircleShape)
          .background(EmeraldPrimary),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (chat.isGroup) Icons.Default.Groups else Icons.Default.Person,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(28.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = chat.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = chat.lastTimestamp,
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = chat.lastMessage,
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )
          if (chat.unreadCount > 0) {
            Badge(
              containerColor = EmeraldPrimary,
              contentColor = Color.White
            ) {
              Text(text = chat.unreadCount.toString(), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
          if (chat.isPinned) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.PushPin, contentDescription = "Pinned", tint = GoldAccent, modifier = Modifier.size(14.dp))
          }
        }
      }
    }
  }
}

@Composable
fun NewGroupDialog(onDismiss: () -> Unit, onCreate: (String, String) -> Unit) {
  var groupName by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Create New Secure Group") },
    text = {
      Column {
        OutlinedTextField(
          value = groupName,
          onValueChange = { groupName = it },
          label = { Text("Group Name") },
          modifier = Modifier.fillMaxWidth().testTag("group_name_input"),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Description") },
          modifier = Modifier.fillMaxWidth().testTag("group_desc_input")
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { if (groupName.isNotBlank()) onCreate(groupName, description) },
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        modifier = Modifier.testTag("create_group_confirm_button")
      ) {
        Text("Create")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
fun NewContactDialog(onDismiss: () -> Unit, onAdd: (String, String, String) -> Unit) {
  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var about by remember { mutableStateOf("Available on Bharat Connect 🇮🇳") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Add New Contact") },
    text = {
      Column {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Contact Name") },
          modifier = Modifier.fillMaxWidth().testTag("contact_name_input"),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("Phone Number (+91...)") },
          modifier = Modifier.fillMaxWidth().testTag("contact_phone_input"),
          singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
          value = about,
          onValueChange = { about = it },
          label = { Text("Status / About") },
          modifier = Modifier.fillMaxWidth().testTag("contact_about_input"),
          singleLine = true
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { if (name.isNotBlank() && phone.isNotBlank()) onAdd(name, phone, about) },
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        modifier = Modifier.testTag("add_contact_confirm_button")
      ) {
        Text("Add Contact")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
