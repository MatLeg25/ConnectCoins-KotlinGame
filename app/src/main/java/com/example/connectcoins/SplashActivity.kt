package com.example.connectcoins

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.connectcoins.ui.theme.ConnectCoinsTheme
import com.example.connectcoins.utils.Utils.ColourText
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConnectCoinsTheme {
                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    fun SplashScreen(modifier: Modifier = Modifier) {
        val alpha = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true, block = {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1500)
            )
            delay(2000)
            startActivity(
                Intent(this@SplashActivity, MainActivity::class.java)
            )
        })

        Box(
            modifier = modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.wallhaven_j5px5q),
                    contentScale = ContentScale.FillBounds
                ),
            contentAlignment = Alignment.Center
        )
         {

            Column(modifier = modifier.alpha(alpha.value)) {
                ColourText(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 32.sp
                )
            }
        }
    }

}
