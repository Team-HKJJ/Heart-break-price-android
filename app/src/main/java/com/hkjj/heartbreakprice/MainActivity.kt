package com.hkjj.heartbreakprice

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hkjj.heartbreakprice.core.routing.NavigationAction
import androidx.compose.ui.res.stringResource
import com.hkjj.heartbreakprice.core.routing.NavigationRoot
import com.hkjj.heartbreakprice.core.routing.NavigationViewModel
import com.hkjj.heartbreakprice.ui.theme.HeartBreakPriceTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val navigationViewModel: NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Handle intent when app starts
        handleIntent(intent)

        setContent {
            HeartBreakPriceTheme {
                var showPermissionDialog by remember { mutableStateOf(false) }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (!isGranted) {
                            showPermissionDialog = true
                        }
                    }
                )

                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                if (showPermissionDialog) {
                    AlertDialog(
                        onDismissRequest = { showPermissionDialog = false },
                        title = { Text(stringResource(R.string.permission_title)) },
                        text = { Text(stringResource(R.string.permission_desc)) },
                        confirmButton = {
                            TextButton(onClick = {
                                showPermissionDialog = false
                                openAppSettings()
                            }) {
                                Text(stringResource(R.string.permission_go_to_settings))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showPermissionDialog = false }) {
                                Text(stringResource(R.string.action_cancel))
                            }
                        }
                    )
                }

                NavigationRoot()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val target = intent?.getStringExtra("navigate_to")
        val type = intent?.getStringExtra("type")
        
        Log.d("MainActivity", "handleIntent target: $target, type: $type")
        
        if (target != null) {
            navigationViewModel.onAction(NavigationAction.NavigateToMain(initialTab = target))
        } else if (type == "TARGET_REACHED") {
            navigationViewModel.onAction(NavigationAction.NavigateToMain(initialTab = "notification"))
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

}