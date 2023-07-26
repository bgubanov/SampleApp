package com.example.sampleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sampleapp.ui.theme.SampleAppTheme


class MainActivity : ComponentActivity() {
    private val settingsIntent
        get() = Intent(
            ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            Uri.parse("package:${applicationContext.packageName}")
        )

    private val activityResultState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val result = remember { activityResultState }
                    Sample(text = result.value) {
                        startActivityForResult(settingsIntent, 1)
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultState.value = when(resultCode) {
            RESULT_OK -> "OK"
            RESULT_CANCELED -> "Canceled"
            else -> "else"
        }
    }
}

@Composable
fun Sample(text: String = "", onButtonClick: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sample App!",
        )
        if (text.isNotBlank()) {
            Text(text)
        }
        Button(onClick = onButtonClick) {
            Text(text = "Open Settings")
        }
    }
}