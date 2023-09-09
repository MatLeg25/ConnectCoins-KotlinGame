package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.MainScreen
import com.example.connectcoins.ui.Screen
import com.example.connectcoins.ui.theme.ConnectCoinsTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConnectCoinsTheme {
                val gameViewModel: GameViewModel = viewModel()
                MainScreen(gameViewModel)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun TopBar(navController: NavController? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Magenta)
            .padding(horizontal = 20.dp)

    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColourText(
                text = stringResource(id = R.string.app_name)
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController!!.navigate(Screen.ConfigScreen.withArgs("Player name :]"))
                    },
                shape = CircleShape,
            ) {
                Image(
                    painterResource(R.drawable.ic_settings),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ColourText(text: String) {
    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.Yellow /*...*/)

    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    ),
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append(text)
            }
        }
    )

}









