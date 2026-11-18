package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class OnboardingStep {
  SPLASH, WELCOME, LANGUAGE, PHONE, OTP, PROFILE, HOME
}

class BharatConnectViewModel : ViewModel() {

  private val _onboardingStep = MutableStateFlow(OnboardingStep.SPLASH)
  val onboardingStep: StateFlow<OnboardingStep> = _onboardingStep.asStateFlow()

  private val _selectedTab = MutableStateFlow(0)
  val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

  private val _currentLanguage = MutableStateFlow("English")
  val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

  private val _phoneNumber = MutableStateFlow("")
  val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

  private val _userProfile = MutableStateFlow(
    UserProfile(
      id = "user_me",
      name = "Aarav Sharma",
      phoneNumber = "+91 98765 43210",
      bharatId = "aarav.bharat",
      avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200",
      about = "Connected Privately, Communicating Freely 🇮🇳"
    )
  )
  val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

  private val _chats = MutableStateFlow(
    listOf(
      Chat(
        id = "chat_1",
        name = "Priya Patel",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200",
        lastMessage = "Namaste! Are we meeting for the project discussion today?",
        lastTimestamp = "10:45 AM",
        unreadCount = 2,
        isPinned = true
      ),
      Chat(
        id = "chat_2",
        name = "Desi Tech Innovators 🇮🇳",
        avatarUrl = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=200",
        isGroup = true,
        membersCount = 14,
        description = "Official group for Indian tech founders and developers.",
        lastMessage = "Rahul: New deployment completed successfully!",
        lastTimestamp = "09:15 AM",
        unreadCount = 5,
        isPinned = true
      ),
      Chat(
        id = "chat_3",
        name = "Vikram Singh",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200",
        lastMessage = "Will send the documentation by evening.",
        lastTimestamp = "Yesterday",
        unreadCount = 0
      ),
      Chat(
        id = "chat_4",
        name = "Ananya Iyer",
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200",
        lastMessage = "Dhanyawad! Great catching up.",
        lastTimestamp = "Yesterday",
        unreadCount = 0
      ),
      Chat(
        id = "chat_5",
        name = "Bharat Connect Support",
        avatarUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=200",
        lastMessage = "Welcome to Bharat Connect! Your secure home for private communication.",
        lastTimestamp = "18/11/26",
        unreadCount = 0,
        isLocked = true
      )
    )
  )
  val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

  private val _messages = MutableStateFlow(
    mapOf(
      "chat_1" to mutableListOf(
        Message("m1", "chat_1", "user_1", "Priya Patel", "Namaste! Are we meeting for the project discussion today?", "10:45 AM", false, MessageType.TEXT),
        Message("m2", "chat_1", "user_me", "Aarav Sharma", "Namaste Priya! Yes, let's connect at 3 PM via Bharat Connect Meeting.", "10:46 AM", true, MessageType.TEXT, status = MessageStatus.READ)
      ),
      "chat_2" to mutableListOf(
        Message("m3", "chat_2", "user_2", "Rahul V.", "Welcome everyone to Bharat Connect!", "09:00 AM", false, MessageType.TEXT),
        Message("m4", "chat_2", "user_me", "Aarav Sharma", "Proud to be on a secure made-in-India platform!", "09:10 AM", true, MessageType.TEXT),
        Message("m5", "chat_2", "user_3", "Rahul V.", "New deployment completed successfully!", "09:15 AM", false, MessageType.TEXT)
      )
    )
  )
  val messages: StateFlow<Map<String, List<Message>>> = _messages.asStateFlow()

  private val _activeChatId = MutableStateFlow<String?>(null)
  val activeChatId: StateFlow<String?> = _activeChatId.asStateFlow()

  private val _calls = MutableStateFlow(
    listOf(
      CallItem("c1", "Priya Patel", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200", CallType.VIDEO, true, false, "Today, 11:30 AM", "12 min"),
      CallItem("c2", "Vikram Singh", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", CallType.VOICE, false, true, "Yesterday, 4:15 PM", "0 min"),
      CallItem("c3", "Desi Tech Innovators 🇮🇳", "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=200", CallType.GROUP_VIDEO, true, false, "16 Nov, 8:00 PM", "45 min")
    )
  )
  val calls: StateFlow<List<CallItem>> = _calls.asStateFlow()

  private val _meetings = MutableStateFlow(
    listOf(
      MeetingItem("m1", "Daily Sprint Standup", "Aarav Sharma", "IND-789-456", "bharat2026", "Today, 4:00 PM", false, 8),
      MeetingItem("m2", "Security Architecture Review", "Vikram Singh", "IND-123-999", "secure99", "Instant Meeting", true, 4)
    )
  )
  val meetings: StateFlow<List<MeetingItem>> = _meetings.asStateFlow()

  private val _statuses = MutableStateFlow(
    listOf(
      StatusItem("s1", "Priya Patel", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200", "1 hour ago", "Working on decentralized encryption protocols.", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=500"),
      StatusItem("s2", "Ananya Iyer", "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", "3 hours ago", "Beautiful morning in Bengaluru! 🇮🇳", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=500")
    )
  )
  val statuses: StateFlow<List<StatusItem>> = _statuses.asStateFlow()

  // Settings State
  private val _darkTheme = MutableStateFlow(false)
  val darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()

  private val _amoledMode = MutableStateFlow(false)
  val amoledMode: StateFlow<Boolean> = _amoledMode.asStateFlow()

  private val _appLockEnabled = MutableStateFlow(false)
  val appLockEnabled: StateFlow<Boolean> = _appLockEnabled.asStateFlow()

  private val _isLocked = MutableStateFlow(false)
  val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

  private val _readReceipts = MutableStateFlow(true)
  val readReceipts: StateFlow<Boolean> = _readReceipts.asStateFlow()

  private val _emergencyMode = MutableStateFlow(false)
  val emergencyMode: StateFlow<Boolean> = _emergencyMode.asStateFlow()

  // Active call / meeting navigation state
  private val _activeCall = MutableStateFlow<CallItem?>(null)
  val activeCall: StateFlow<CallItem?> = _activeCall.asStateFlow()

  private val _activeMeeting = MutableStateFlow<MeetingItem?>(null)
  val activeMeeting: StateFlow<MeetingItem?> = _activeMeeting.asStateFlow()

  fun setOnboardingStep(step: OnboardingStep) {
    _onboardingStep.value = step
  }

  fun setSelectedTab(tab: Int) {
    _selectedTab.value = tab
  }

  fun setLanguage(lang: String) {
    _currentLanguage.value = lang
  }

  fun setPhoneNumber(phone: String) {
    _phoneNumber.value = phone
  }

  fun updateProfile(name: String, about: String) {
    _userProfile.update { it.copy(name = name, about = about) }
  }

  fun setActiveChat(chatId: String?) {
    _activeChatId.value = chatId
    if (chatId != null) {
      // Clear unread
      _chats.update { list ->
        list.map { if (it.id == chatId) it.copy(unreadCount = 0) else it }
      }
    }
  }

  fun sendMessage(chatId: String, text: String, type: MessageType = MessageType.TEXT, mediaUrl: String? = null) {
    if (text.isBlank() && mediaUrl == null) return
    val newMessage = Message(
      id = "msg_${System.currentTimeMillis()}",
      chatId = chatId,
      senderId = "user_me",
      senderName = _userProfile.value.name,
      text = text,
      timestamp = "Just now",
      isMe = true,
      type = type,
      mediaUrl = mediaUrl,
      status = MessageStatus.SENT
    )
    _messages.update { map ->
      val mutableMap = map.toMutableMap()
      val list = mutableMap[chatId]?.toMutableList() ?: mutableListOf()
      list.add(newMessage)
      mutableMap[chatId] = list
      mutableMap
    }
    // Update last message in chat list
    _chats.update { list ->
      list.map { chat ->
        if (chat.id == chatId) {
          chat.copy(
            lastMessage = if (text.isNotBlank()) text else "[Media Attachment]",
            lastTimestamp = "Just now"
          )
        } else chat
      }
    }
  }

  fun createGroup(name: String, description: String) {
    val newChat = Chat(
      id = "chat_${System.currentTimeMillis()}",
      name = name,
      avatarUrl = "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=200",
      isGroup = true,
      membersCount = 3,
      description = description,
      lastMessage = "Group created successfully",
      lastTimestamp = "Just now",
      isPinned = true
    )
    _chats.update { listOf(newChat) + it }
    _messages.update { map ->
      map + (newChat.id to mutableListOf(
        Message("m_init", newChat.id, "user_me", "Aarav Sharma", "Group '$name' created. End-to-end encrypted.", "Just now", true)
      ))
    }
  }

  fun addContact(name: String, phoneNumber: String, about: String) {
    val newChat = Chat(
      id = "chat_${System.currentTimeMillis()}",
      name = name,
      avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200",
      isGroup = false,
      lastMessage = "Connected securely with $phoneNumber",
      lastTimestamp = "Just now",
      unreadCount = 0,
      isPinned = false
    )
    _chats.update { listOf(newChat) + it }
    _messages.update { map ->
      map + (newChat.id to mutableListOf(
        Message("m_init_${System.currentTimeMillis()}", newChat.id, "user_new", name, "Namaste! I am now connected with you on Bharat Connect. $about", "Just now", false)
      ))
    }
  }

  fun scheduleMeeting(title: String, time: String) {
    val newMeeting = MeetingItem(
      id = "m_${System.currentTimeMillis()}",
      title = title,
      hostName = _userProfile.value.name,
      meetingCode = "IND-${(100..999).random()}-${(100..999).random()}",
      password = "ind${(1000..9999).random()}",
      scheduledTime = time,
      isInstant = false,
      participantsCount = 1
    )
    _meetings.update { listOf(newMeeting) + it }
  }

  fun startCall(call: CallItem) {
    _activeCall.value = call
  }

  fun endCall() {
    _activeCall.value = null
  }

  fun joinMeeting(meeting: MeetingItem) {
    _activeMeeting.value = meeting
  }

  fun leaveMeeting() {
    _activeMeeting.value = null
  }

  fun toggleDarkTheme(enabled: Boolean) {
    _darkTheme.value = enabled
  }

  fun toggleAmoledMode(enabled: Boolean) {
    _amoledMode.value = enabled
    if (enabled) _darkTheme.value = true
  }

  fun toggleAppLock(enabled: Boolean) {
    _appLockEnabled.value = enabled
  }

  fun setLocked(locked: Boolean) {
    _isLocked.value = locked
  }

  fun toggleReadReceipts(enabled: Boolean) {
    _readReceipts.value = enabled
  }

  fun toggleEmergencyMode(enabled: Boolean) {
    _emergencyMode.value = enabled
  }
}
