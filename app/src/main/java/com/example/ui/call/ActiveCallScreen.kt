package com.example.ui.call

import androidx.compose.runtime.Composable
import com.example.data.CallItem
import com.example.data.CallType
import com.example.viewmodel.BharatConnectViewModel

@Composable
fun ActiveCallScreen(call: CallItem, viewModel: BharatConnectViewModel) {
  CallScreen(
    callerName = call.name,
    callerAvatar = call.avatarUrl,
    isVideoCall = call.type == CallType.VIDEO || call.type == CallType.GROUP_VIDEO,
    onEndCall = { viewModel.endCall() }
  )
}
