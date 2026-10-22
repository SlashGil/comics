package com.slashgil.comics.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slashgil.comics.ui.theme.BgSurface
import com.slashgil.comics.ui.theme.ComicsBlue
import com.slashgil.comics.ui.theme.TextSecondary

@Composable
fun PublisherFilterRow(
    publishers: List<String>,
    selectedPublisher: String,
    onPublisherSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(publishers) { publisher ->
            val isSelected = publisher.equals(selectedPublisher, ignoreCase = true)
            FilterChip(
                selected = isSelected,
                onClick = { onPublisherSelected(publisher) },
                label = {
                    Text(
                        text = publisher,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = BgSurface,
                    labelColor = TextSecondary,
                    selectedContainerColor = ComicsBlue,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = Color.Transparent,
                    selectedBorderColor = Color.Transparent
                )
            )
        }
    }
}
