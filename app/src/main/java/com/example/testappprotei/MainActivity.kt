package com.example.testappprotei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.testappprotei.repository.dataBase.Dependencies
import com.example.testappprotei.presentation.navigation.BaseNavGraph
import com.example.testappprotei.ui.theme.TestAppProteiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)
        setContent {

            val navController = rememberNavController()
            TestAppProteiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    BaseNavGraph(navController)
                }
            }
        }
    }
}