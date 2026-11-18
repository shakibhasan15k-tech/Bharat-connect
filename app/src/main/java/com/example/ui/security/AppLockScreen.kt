package com.example.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun AppLockScreen(viewModel: BharatConnectViewModel) {
  var pin by remember { mutableStateOf("") }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(EmeraldPrimary),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Lock, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(40.dp))
      }
      Spacer(modifier = Modifier.height(24.dp))
      Text("Bharat Connect is Locked", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
      Spacer(modifier = Modifier.height(8.dp))
      Text("Enter your security PIN or use fingerprint authentication", fontSize = 14.sp, color = Color.LightGray, textAlign = TextAlign.Center)
      Spacer(modifier = Modifier.height(32.dp))

      OutlinedTextField(
        value = pin,
        onValueChange = { pin = it },
        label = { Text("Security PIN", color = Color.LightGray) },
        modifier = Modifier.width(200.dp).testTag("app_lock_pin_input"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = GoldAccent,
          unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
          focusedTextColor = Color.White,
          unfocusedTextColor = Color.White,
          focusedLabelColor = GoldAccent
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = { viewModel.setLocked(false) },
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("unlock_app_button"),
        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text("Unlock App", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }

      Spacer(modifier = Modifier.height(16.dp))

      IconButton(
        onClick = { viewModel.setLocked(false) },
        modifier = Modifier.size(56.dp).testTag("biometric_unlock_button")
      ) {
        Icon(Icons.Default.Fingerprint, contentDescription = "Biometric", tint = GoldAccent, modifier = Modifier.size(36.dp))
      }
    }
  }
}
