package com.hkjj.heartbreakprice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hkjj.heartbreakprice.core.routing.NavigationRoot
import com.hkjj.heartbreakprice.ui.theme.HeartBreakPriceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeartBreakPriceTheme {
                NavigationRoot()
            }
        }
    }
}