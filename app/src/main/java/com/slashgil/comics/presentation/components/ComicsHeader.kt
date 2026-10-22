package com.slashgil.comics.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slashgil.comics.ui.theme.BgSurface
import com.slashgil.comics.ui.theme.ComicsBlue

@Composable
fun ComicsHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(BgSurface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "COMICS",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 6.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(ComicsBlue)
                .align(Alignment.BottomCenter)
        )
    }
}
