package com.example.connectcoins.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.utils.Utils.COIN_BRUSH_COLORS

@Preview(showBackground = true, widthDp = 500)
@Composable
fun TopBar(
    gameUiState: GameUiState = GameUiState(Player("a", COIN_BRUSH_COLORS[0])),
    navController: NavController? = null,
    toggleModalFunction: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Gray,
                        Color.Black,
                        Color.DarkGray
                    )
                )
            ),
        contentAlignment = Alignment.BottomCenter


    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.clickable { toggleModalFunction.invoke() }
            ) {
                Image(
                    painterResource(R.drawable.ic_stats),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            )
            {
                CurrentPlayerLabel(gameUiState)
            }


            Card(
                modifier = Modifier.clickable { navController!!.navigate(Screen.ConfigScreen.route) },
                shape = CircleShape,
            ) {
                Image(
                    painterResource(R.drawable.ic_settings),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }

        }
    }
}



