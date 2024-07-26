package me.theek.nr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import me.theek.nr.navigation.AppNavigator
import me.theek.nr.ui.theme.NotificationRecorderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationRecorderTheme {
                AppNavigator()
            }
        }
    }
}

//private fun startNotificationListenerService() {
//    val intent = Intent(this, NotificationListener::class.java)
//    startService(intent)
//}