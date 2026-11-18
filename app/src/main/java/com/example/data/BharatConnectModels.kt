package com.example.data

data class UserProfile(
  val id: String,
  val name: String,
  val phoneNumber: String,
  val bharatId: String,
  val avatarUrl: String,
  val about: String,
  val isVerified: Boolean = true
)

data class Message(
  val id: String,
  val chatId: String,
  val senderId: String,
  val senderName: String,
  val text: String,
  val timestamp: String,
  val isMe: Boolean,
  val type: MessageType = MessageType.TEXT,
  val mediaUrl: String? = null,
  val status: MessageStatus = MessageStatus.SENT,
  val reactions: Map<String, Int> = emptyMap(),
  val isPinned: Boolean = false,
  val isStarred: Boolean = false,
  val isEdited: Boolean = false
)

enum class MessageType {
  TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT, LOCATION, STICKER, POLL
}

enum class MessageStatus {
  SENDING, SENT, DELIVERED, READ
}

data class Chat(
  val id: String,
  val name: String,
  val avatarUrl: String,
  val isGroup: Boolean = false,
  val unreadCount: Int = 0,
  val lastMessage: String,
  val lastTimestamp: String,
  val isPinned: Boolean = false,
  val isArchived: Boolean = false,
  val membersCount: Int = 2,
  val description: String = "",
  val isLocked: Boolean = false
)

data class CallItem(
  val id: String,
  val name: String,
  val avatarUrl: String,
  val type: CallType,
  val isIncoming: Boolean,
  val isMissed: Boolean,
  val timestamp: String,
  val duration: String
)

enum class CallType {
  VOICE, VIDEO, GROUP_VOICE, GROUP_VIDEO
}

data class MeetingItem(
  val id: String,
  val title: String,
  val hostName: String,
  val meetingCode: String,
  val password: String,
  val scheduledTime: String,
  val isInstant: Boolean,
  val participantsCount: Int
)

data class StatusItem(
  val id: String,
  val userName: String,
  val userAvatar: String,
  val timestamp: String,
  val caption: String,
  val mediaUrl: String,
  val isViewed: Boolean = false
)
