package com.example.connectcoins.ui.screens.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(textResId: Int) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )) {
        Text(
            text = stringResource(id = textResId),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

}
