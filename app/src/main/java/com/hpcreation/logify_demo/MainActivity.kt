package com.hpcreation.logify_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hpcreation.logify_android.logDebug
import com.hpcreation.logify_android.logError
import com.hpcreation.logify_android.logInfo
import com.hpcreation.logify_android.logWarn
import com.hpcreation.logify_demo.ui.theme.LogifyDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        logDebug("Activity created")
        logInfo("Loading user data")
        logWarn("Cache is stale, refetching")

        val user = User(id = 1, name = "Harsh", email = "harsh@example.com")
        //logInfo(user)

        try {
            throw RuntimeException("Simulated network failure")
        } catch (e: Exception) {
            logError("Request failed", e)
        }

        // ── Custom tag ───────────────────────────────────────
        logDebug("Custom tag log", tag = "BootFlow")

        setContent {
            LogifyDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LogifyDemoTheme {
        Greeting("Android")
    }
}