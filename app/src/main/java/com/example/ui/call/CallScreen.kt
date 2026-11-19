package com.example.ui.call

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent

@Composable
fun CallScreen(
  callerName: String = "Priya Patel",
  callerAvatar: String = "",
  isVideoCall: Boolean = true,
  onEndCall: () -> Unit = {}
) {
  var isMuted by remember { mutableStateOf(false) }
  var isSpeakerOn by remember { mutableStateOf(true) }
  var isVideoOn by remember { mutableStateOf(isVideoCall) }

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
      // Top Status Header
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(GoldAccent)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isVideoOn) "Bharat Connect HD Video Call" else "Bharat Connect HD Voice Call",
            color = GoldAccent,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      // Center Avatar / Video Box
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
          modifier = Modifier
            .size(140.dp)
            .clip(CircleShape)
            .background(EmeraldPrimary)
            .padding(4.dp),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .background(Color(0xFF138838)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(72.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          text = callerName,
          color = Color.White,
          fontSize = 26.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Encrypted Connection • 03:42",
          color = Color.LightGray,
          fontSize = 14.sp
        )
      }

      // Bottom Control Buttons
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Mute Button
        IconButton(
          onClick = { isMuted = !isMuted },
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isMuted) GoldAccent else Color.White.copy(alpha = 0.2f))
            .testTag("mute_call_button")
        ) {
          Icon(
            imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            contentDescription = "Mute",
            tint = if (isMuted) Color.Black else Color.White
          )
        }

        // Speakerphone Button
        IconButton(
          onClick = { isSpeakerOn = !isSpeakerOn },
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isSpeakerOn) GoldAccent else Color.White.copy(alpha = 0.2f))
            .testTag("speaker_call_button")
        ) {
          Icon(
            imageVector = if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
            contentDescription = "Speakerphone",
            tint = if (isSpeakerOn) Color.Black else Color.White
          )
        }

        // Video Toggle Button
        IconButton(
          onClick = { isVideoOn = !isVideoOn },
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(if (isVideoOn) EmeraldPrimary else Color.White.copy(alpha = 0.2f))
            .testTag("video_call_button")
        ) {
          Icon(
            imageVector = if (isVideoOn) Icons.Default.Videocam else Icons.Default.VideocamOff,
            contentDescription = "Video",
            tint = Color.White
          )
        }

        // End Call Button
        IconButton(
          onClick = onEndCall,
          modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
            .background(Color.Red)
            .testTag("end_call_button")
        ) {
          Icon(
            imageVector = Icons.Default.CallEnd,
            contentDescription = "End Call",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }
  }
}
