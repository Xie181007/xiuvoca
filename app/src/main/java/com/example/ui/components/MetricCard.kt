package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.Secondary
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryContainer

@Composable
fun MetricCard(
  totalCount: Int,
  activeCount: Int,
  completedCount: Int,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("summary_metric_card")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      // Total
      MetricColumn(
        label = "Total",
        value = totalCount.toString(),
        valueColor = Primary,
        modifier = Modifier.weight(1f),
        tag = "metric_total"
      )

      VerticalDivider(
        color = SurfaceContainer,
        modifier = Modifier.height(36.dp),
        thickness = 1.dp
      )

      // Aktif
      MetricColumn(
        label = "Aktif",
        value = activeCount.toString(),
        valueColor = TertiaryContainer,
        modifier = Modifier.weight(1f),
        tag = "metric_active"
      )

      VerticalDivider(
        color = SurfaceContainer,
        modifier = Modifier.height(36.dp),
        thickness = 1.dp
      )

      // Selesai
      MetricColumn(
        label = "Selesai",
        value = completedCount.toString(),
        valueColor = Secondary,
        modifier = Modifier.weight(1f),
        tag = "metric_completed"
      )
    }
  }
}

@Composable
private fun MetricColumn(
  label: String,
  value: String,
  valueColor: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier,
  tag: String
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = label,
      color = OnSurfaceVariant,
      fontSize = 12.sp,
      fontWeight = FontWeight.Medium
    )
    Text(
      text = value,
      color = valueColor,
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.testTag(tag)
    )
  }
}
