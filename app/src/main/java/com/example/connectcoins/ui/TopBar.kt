package com.example.connectcoins.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectcoins.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Preview(showBackground = true, widthDp = 500)
@Composable
fun TopBar(navController: NavController? = null) {

    var time by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1.seconds)
            time++
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Gray,
                        Color.Black,
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
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
            ColourText(
                text = stringResource(id = R.string.time_X, time)
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController!!.navigate(Screen.ConfigScreen.route)
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



