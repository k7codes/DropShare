package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.ekranlar.DropShareAnaArayuz
import com.example.ui.theme.DropShareTheme
import com.example.ui.viewmodel.DropShareViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DropShareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Handle incoming Share Intent ("Paylaş -> DropShare")
        handleIncomingShareIntent(intent)

        setContent {
            DropShareTheme {
                DropShareAnaArayuz(viewModel = viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIncomingShareIntent(intent)
    }

    private fun handleIncomingShareIntent(intent: Intent?) {
        if (intent == null) return
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (sharedText != null) {
                    viewModel.metinGonder(sharedText)
                }
            } else {
                val streamUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                if (streamUri != null) {
                    viewModel.dosyaEkleUriListesi(listOf(streamUri))
                }
            }
        } else if (Intent.ACTION_SEND_MULTIPLE == action && type != null) {
            val streamUris = intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
            if (streamUris != null) {
                viewModel.dosyaEkleUriListesi(streamUris)
            }
        }
    }
}
