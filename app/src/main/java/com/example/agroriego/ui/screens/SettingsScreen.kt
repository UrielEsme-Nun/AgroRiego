package com.example.agroriego.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.agroriego.R
import com.example.agroriego.ui.viewmodel.RiegoViewModel

@Composable
fun SettingsScreen(viewModel: RiegoViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_settings),
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.alertasClima,
                onCheckedChange = { viewModel.onAlertasToggle(it) }
            )
            Text(
                text = stringResource(R.string.label_enable_alerts),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.recordatorios,
                onCheckedChange = { viewModel.onRecordatoriosToggle(it) }
            )
            Text(
                text = stringResource(R.string.label_enable_reminders),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}