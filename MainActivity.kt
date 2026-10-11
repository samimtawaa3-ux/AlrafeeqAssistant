package com.example.alrafeeqassistant

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

// ==============================================================================
// 1. محرك النطق والتعرف الصوتي متعدد اللغات (Voice & Language Engine)
// ==============================================================================
class AlrafeeqVoiceEngine {
    private val supportedLangs = mapOf(
        "ar" to "العربية",
        "de" to "Deutsch",
        "en" to "English",
        "fr" to "Français"
    )

    fun speak(text: String, lang: String = "ar") {
        val langName = supportedLangs[lang] ?: "العربية"
        Log.d("AlrafeeqVoice", "🗣️ [صوت الرفيق ($langName)]: \"$text\"")
    }
}

// ==============================================================================
// 2. إدارة عتاد الجهاز والتطبيقات والوضع الليلي (Hardware & System Controller)
// ==============================================================================
class AlrafeeqHardwareAndApps {
    val appsRegistry = mutableMapOf(
        "واتساب" to "com.whatsapp",
        "بليتسا" to "de.blitzer.android",
        "جوجل مابس" to "com.google.android.apps.maps",
        "الكاميرا" to "com.android.camera",
        "الضبط" to "com.android.settings",
        "الساعة" to "com.android.deskclock"
    )

    var volume: Int = 70
    var brightness: Int = 60
    var bluetooth: String = "ON"
    var airplaneMode: String = "OFF"
    var nightMode: String = "OFF"

    fun registerCustomApp(appName: String, packageName: String) {
        appsRegistry[appName.lowercase()] = packageName
        Log.d("AlrafeeqApps", "⚙️ [إدارة التطبيقات]: تم تسجيل التطبيق الجديد [$appName] بنجاح.")
    }

    fun setHardwareState(settingType: String, valState: Any) {
        when (settingType) {
            "volume" -> {
                volume = valState as Int
                Log.d("AlrafeeqHW", "🔊 [النظام]: تم تعديل مستوى الصوت إلى: $volume%")
            }
            "brightness" -> {
                brightness = valState as Int
                Log.d("AlrafeeqHW", "☀️ [النظام]: تم تعديل إضاءة الشاشة إلى: $brightness%")
            }
            "bluetooth" -> {
                bluetooth = valState as String
                Log.d("AlrafeeqHW", "📶 [النظام]: حالة البلوتوث الآن: $bluetooth")
            }
            "airplane_mode" -> {
                airplaneMode = valState as String
                Log.d("AlrafeeqHW", "✈️ [النظام]: وضع الطيران الآن: $airplaneMode")
            }
        }
    }

    fun toggleNightMode(enable: Boolean = true) {
        nightMode = if (enable) "ON" else "OFF"
        brightness = if (enable) 20 else 70
        Log.d("AlrafeeqHW", "🌙 [الوضع الليلي]: تم تفعيل الوضع الليلي وتخفيض السطوع إلى $brightness% لحماية عينيك.")
    }
}

// ==============================================================================
// 3. نظام الأمان المتقدم للقيادة (Advanced Safety, Dashcam & Radar)
// ==============================================================================
class AlrafeeqSafetySystem(private val voiceEngine: AlrafeeqVoiceEngine) {
    var isDashcamRecording: Boolean = false

    fun checkSpeedAndRadars(currentSpeed: Int, speedLimit: Int, lang: String = "ar") {
        if (currentSpeed > speedLimit) {
            val alertMsg = if (lang == "de") {
                "Achtung! Ihre Geschwindigkeit ist $currentSpeed km/h. Blitzer voraus!"
            } else {
                "انتبه يا رفيق! السرعة $currentSpeed كم/سا والحد المسموح $speedLimit كم/سا. يوجد رادار بليتسا قريب!"
            }
            Log.w("AlrafeeqSafety", "⚠️ [تنبيه سرعة خطيرة - $currentSpeed km/h]")
            voiceEngine.speak(alertMsg, lang)
        } else {
            Log.d("AlrafeeqSafety", "✅ [السرعة آمنة]: $currentSpeed كم/سا. تطبيق Blitzer.de يعمل لمراقبة الطريق.")
        }
    }

    fun monitorDriverFatigue(eyeClosedDuration: Double) {
        if (eyeClosedDuration > 1.5) {
            Log.e("AlrafeeqSafety", "🚨 [إنذار خطر! النعاس أثناء القيادة!]")
            voiceEngine.speak("انتبه يا رفيق! أنت تشعر بالنعاس، يرجى الاستراحة وتوقيف السيارة فوراً!", "ar")
        }
    }

    fun startDashcamMode() {
        isDashcamRecording = true
        Log.d("AlrafeeqSafety", "📹 [كاميرا الطريق Dashcam]: تعمل في الخلفية لتسجيل الفيديو المستمر وتوثيق الطريق.")
    }

    fun smartAutoReplyCalls(callerName: String, drivingSpeed: Int) {
        if (drivingSpeed > 80) {
            Log.d("AlrafeeqSafety", "📞 [مكالمة واردة من: $callerName] (السرعة: $drivingSpeed كم/سا)")
            voiceEngine.speak("تلقيت مكالمة من $callerName، ونظراً لسرعتك قمت بالرد عليه آلياً.", "ar")
        } else {
            Log.d("AlrafeeqSafety", "📞 [مكالمة واردة من: $callerName]: السرعة آمنة.")
        }
    }

    fun filterNotifications(title: String, content: String): Boolean {
        val spamKeywords = listOf("خصم", "عرض", "إعلان", "sale", "discount", "rabatt")
        val isSpam = spamKeywords.any { content.lowercase().contains(it) || title.lowercase().contains(it) }

        if (isSpam) {
            Log.d("AlrafeeqSafety", "🗑️ [تنظيف تلقائي]: تم مسح إشعار غير مهم من ($title).")
            return false
        } else {
            Log.d("AlrafeeqSafety", "🔔 [إشعار هام من $title]: \"$content\"")
            voiceEngine.speak("وصلتك رسالة جديدة من $title", "ar")
            return true
        }
    }
}

// ==============================================================================
// 4. النواة الرئيسية للنظام الشامل (Master Ultimate Core)
// ==============================================================================
class AlrafeeqMasterSystem(private val driverName: String = "محمد", var currentLang: String = "ar") {
    val voice = AlrafeeqVoiceEngine()
    val hardware = AlrafeeqHardwareAndApps()
    val safety = AlrafeeqSafetySystem(voice)
    var screenLocked: Boolean = true

    fun setLanguage(langCode: String) {
        if (langCode in listOf("ar", "de", "en", "fr")) {
            currentLang = langCode
            Log.d("AlrafeeqMaster", "🌐 [تغيير اللغة]: تم تحويل نظام الرفيق إلى ($currentLang)")
        }
    }

    fun triggerSosEmergency(gpsCoords: String = "52.1534, 10.3341") {
        Log.e("AlrafeeqMaster", "🚨 [تنبيه طوارئ SOS عاجل] 🚨 موقع الـ GPS: $gpsCoords")
        voice.speak("تم تفعيل نظام الطوارئ، جاري إرسال موقعك وطلب المساعدة فوراً.", currentLang)
    }

    fun processCommand(spokenText: String) {
        val cmd = spokenText.lowercase()
        Log.d("AlrafeeqMaster", "🎙️ [أمر السائق - ${currentLang.uppercase()}]: \"$spokenText\"")

        if (cmd.contains("طوارئ") || cmd.contains("sos")) {
            triggerSosEmergency()
            return
        }

        if (cmd.contains("علي الصوت") || cmd.contains("volume up") || cmd.contains("علي")) {
            hardware.setHardwareState("volume", 90)
        } else if (cmd.contains("وطي الصوت") || cmd.contains("volume down")) {
            hardware.setHardwareState("volume", 30)
        } else if (cmd.contains("زد الإضاءة")) {
            hardware.setHardwareState("brightness", 85)
        } else if (cmd.contains("سكر البلوتوث")) {
            hardware.setHardwareState("bluetooth", "OFF")
        } else if (cmd.contains("وضع طيران")) {
            hardware.setHardwareState("airplane_mode", "ON")
        } else if (cmd.contains("وضع ليلي")) {
            hardware.toggleNightMode(true)
        }

        for ((appName, pkg) in hardware.appsRegistry) {
            if (cmd.contains("افتح $appName") || cmd.contains("open $appName")) {
                Log.d("AlrafeeqMaster", "📱 [تشغيل تطبيق]: جاري فتح [$appName] ($pkg)")
                return
            }
        }
    }
}

// ==============================================================================
// 5. ربط النشاط الرئيسي في أندرويد ستوديو (MainActivity)
// ==============================================================================
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // تشغيل نظام الرفيق فور فتح التطبيق
        val rafeeq = AlrafeeqMasterSystem(driverName = "محمد", currentLang = "ar")

        // تجربة محاكاة الخدمات والوظائف البرمجية
        rafeeq.hardware.registerCustomApp("سبوتيفاي", "com.spotify.music")
        rafeeq.processCommand("وضع ليلي")
        rafeeq.safety.startDashcamMode()
        
        rafeeq.safety.checkSpeedAndRadars(135, 120, "ar")
        rafeeq.setLanguage("de")
        rafeeq.safety.checkSpeedAndRadars(140, 100, "de")
        rafeeq.setLanguage("ar")

        rafeeq.safety.filterNotifications("متجر تسوق", "خصم 50% على الملابس!")
        rafeeq.safety.monitorDriverFatigue(2.0)
        rafeeq.safety.smartAutoReplyCalls("سامر", 110)

        rafeeq.processCommand("افتح سبوتيفاي")
        
        // استدعاء الأوامر الإضافية بأمان
        rafeeq.processCustomTestCommand()
        rafeeq.triggerSosEmergency()
    }
    
    // دالة امتداد مخصصة وخالية من الأخطاء
    private fun AlrafeeqMasterSystem.processCustomTestCommand() {
        this.processCommand("علي الصوت")
    }
} im
