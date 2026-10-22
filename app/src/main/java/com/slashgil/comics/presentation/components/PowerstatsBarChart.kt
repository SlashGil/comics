package com.slashgil.comics.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slashgil.comics.domain.model.Powerstats
import com.slashgil.comics.ui.theme.ComicsCrimson
import com.slashgil.comics.ui.theme.PowerBarBg
import com.slashgil.comics.ui.theme.TextSecondary

@Composable
fun PowerstatsBarChart(
    powerstats: Powerstats,
    modifier: Modifier = Modifier
) {
    val statsList = listOf(
        "Intelligence" to powerstats.intelligence,
        "Strength" to powerstats.strength,
        "Speed" to powerstats.speed,
        "Durability" to powerstats.durability,
        "Power" to powerstats.power,
        "Combat" to powerstats.combat
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        statsList.forEach { (label, value) ->
            PowerstatRow(label = label, value = value)
        }
    }
}

@Composable
private fun PowerstatRow(
    label: String,
    value: Int
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(value) {
        val target = (value.coerceIn(0, 100) / 100f)
        animatedProgress.animateTo(
            targetValue = target,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(90.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(PowerBarBg)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress.value)
                    .clip(RoundedCornerShape(5.dp))
                    .background(getStatColor(label, value))
            )
        }

        Text(
            text = value.toString(),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 10.dp)
                .width(30.dp)
        )
    }
}

private fun getStatColor(label: String, value: Int): Color {
    return when (label) {
        "Intelligence" -> Color(0xFF38BDF8)
        "Strength" -> ComicsCrimson
        "Speed" -> Color(0xFFFACC15)
        "Durability" -> Color(0xFF4ADE80)
        "Power" -> Color(0xFFA855F7)
        "Combat" -> Color(0xFFFB923C)
        else -> ComicsCrimson
    }
}
