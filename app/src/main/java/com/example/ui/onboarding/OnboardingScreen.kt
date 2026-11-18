package com.example.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.BharatConnectViewModel
import com.example.viewmodel.OnboardingStep

@Composable
fun OnboardingScreen(viewModel: BharatConnectViewModel) {
  val step by viewModel.onboardingStep.collectAsState()
  val language by viewModel.currentLanguage.collectAsState()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(EmeraldPrimary, Color(0xFF00331A))
        )
      )
  ) {
    when (step) {
      OnboardingStep.SPLASH -> SplashScreen(onNext = { viewModel.setOnboardingStep(OnboardingStep.WELCOME) })
      OnboardingStep.WELCOME -> WelcomeScreen(
        onLanguageSelected = { lang ->
          viewModel.setLanguage(lang)
          viewModel.setOnboardingStep(OnboardingStep.PHONE)
        }
      )
      OnboardingStep.PHONE -> PhoneRegistrationScreen(
        onPhoneEntered = { phone ->
          viewModel.setPhoneNumber(phone)
          viewModel.setOnboardingStep(OnboardingStep.OTP)
        }
      )
      OnboardingStep.OTP -> OtpVerificationScreen(
        onVerified = { viewModel.setOnboardingStep(OnboardingStep.PROFILE) }
      )
      OnboardingStep.PROFILE -> ProfileSetupScreen(
        onComplete = { name, about ->
          viewModel.updateProfile(name, about)
          viewModel.setOnboardingStep(OnboardingStep.HOME)
        }
      )
      else -> {}
    }
  }
}

@Composable
fun SplashScreen(onNext: () -> Unit) {
  LaunchedEffect(Unit) {
    kotlinx.coroutines.delay(2000)
    onNext()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .background(Color.White),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Security,
        contentDescription = "Logo",
        tint = EmeraldPrimary,
        modifier = Modifier.size(56.dp)
      )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
      text = "BHARAT CONNECT",
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold,
      color = Color.White,
      letterSpacing = 2.sp
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Connect Privately. Communicate Freely. 🇮🇳",
      fontSize = 14.sp,
      color = GoldAccent,
      textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(48.dp))
    CircularProgressIndicator(color = GoldAccent, modifier = Modifier.size(36.dp))
  }
}

@Composable
fun WelcomeScreen(onLanguageSelected: (String) -> Unit) {
  var selectedLang by remember { mutableStateOf("English") }
  val languages = listOf("English", "हिन्दी (Hindi)", "বাংলা (Bengali)", "తెలుగు (Telugu)", "मराठी (Marathi)", "தமிழ் (Tamil)")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Spacer(modifier = Modifier.height(48.dp))
      Text("🇮🇳", fontSize = 48.sp)
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "Welcome to Bharat Connect",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "Choose your preferred language to get started",
        fontSize = 14.sp,
        color = Color.LightGray,
        textAlign = TextAlign.Center
      )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
      languages.forEach { lang ->
        val isSelected = selectedLang == lang
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { selectedLang = lang },
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) EmeraldSecondary else Color.White.copy(alpha = 0.1f)
          ),
          shape = RoundedCornerShape(12.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(lang, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            if (isSelected) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GoldAccent)
            }
          }
        }
      }
    }

    Button(
      onClick = { onLanguageSelected(selectedLang) },
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .testTag("continue_lang_button"),
      colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
      shape = RoundedCornerShape(16.dp)
    ) {
      Text("Continue", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun PhoneRegistrationScreen(onPhoneEntered: (String) -> Unit) {
  var phone by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Spacer(modifier = Modifier.height(48.dp))
      Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(64.dp))
      Spacer(modifier = Modifier.height(16.dp))
      Text("Enter Your Phone Number", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
      Spacer(modifier = Modifier.height(8.dp))
      Text("Bharat Connect will send an SMS verification code to confirm your number.", fontSize = 14.sp, color = Color.LightGray, textAlign = TextAlign.Center)
      Spacer(modifier = Modifier.height(32.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = "+91",
          readOnly = true,
          onValueChange = {},
          modifier = Modifier.width(90.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldAccent,
            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          placeholder = { Text("98765 43210", color = Color.Gray) },
          modifier = Modifier
            .weight(1f)
            .testTag("phone_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GoldAccent,
            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          singleLine = true
        )
      }
    }

    Button(
      onClick = { if (phone.isNotBlank()) onPhoneEntered(phone) },
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .testTag("verify_phone_button"),
      colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
      shape = RoundedCornerShape(16.dp),
      enabled = phone.isNotBlank()
    ) {
      Text("Verify Number", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun OtpVerificationScreen(onVerified: () -> Unit) {
  var otp by remember { mutableStateOf("7865") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Spacer(modifier = Modifier.height(48.dp))
      Icon(Icons.Default.Lock, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(64.dp))
      Spacer(modifier = Modifier.height(16.dp))
      Text("Enter Verification Code", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
      Spacer(modifier = Modifier.height(8.dp))
      Text("We have sent a 4-digit SMS verification code to your phone.", fontSize = 14.sp, color = Color.LightGray, textAlign = TextAlign.Center)
      Spacer(modifier = Modifier.height(32.dp))

      OutlinedTextField(
        value = otp,
        onValueChange = { otp = it },
        modifier = Modifier
          .width(180.dp)
          .testTag("otp_input"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = GoldAccent,
          unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
          focusedTextColor = Color.White,
          unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )
    }

    Button(
      onClick = { onVerified() },
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .testTag("confirm_otp_button"),
      colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
      shape = RoundedCornerShape(16.dp)
    ) {
      Text("Confirm & Continue", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun ProfileSetupScreen(onComplete: (String, String) -> Unit) {
  var name by remember { mutableStateOf("Aarav Sharma") }
  var about by remember { mutableStateOf("Connected Privately, Communicating Freely 🇮🇳") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Spacer(modifier = Modifier.height(32.dp))
      Text("Set Up Your Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
      Spacer(modifier = Modifier.height(8.dp))
      Text("Enter your name and status for your Bharat Connect ID", fontSize = 14.sp, color = Color.LightGray, textAlign = TextAlign.Center)
      Spacer(modifier = Modifier.height(32.dp))

      Box(
        modifier = Modifier
          .size(100.dp)
          .clip(CircleShape)
          .background(EmeraldSecondary)
          .border(2.dp, GoldAccent, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(56.dp))
      }

      Spacer(modifier = Modifier.height(32.dp))

      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Your Name", color = Color.LightGray) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("profile_name_input"),
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

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = about,
        onValueChange = { about = it },
        label = { Text("About Status", color = Color.LightGray) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("profile_about_input"),
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
    }

    Button(
      onClick = { if (name.isNotBlank()) onComplete(name, about) },
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .testTag("complete_profile_button"),
      colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
      shape = RoundedCornerShape(16.dp),
      enabled = name.isNotBlank()
    ) {
      Text("Start Messaging", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}
