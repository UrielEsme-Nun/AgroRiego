package com.example.agroriego.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.agroriego.R
import com.example.agroriego.data.model.TurnoRiego
import com.example.agroriego.ui.viewmodel.RiegoViewModel

@Composable
fun HomeScreen(viewModel: RiegoViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_home),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.listaTurnos) { turno ->
                ItemTurnoCard(turno = turno)
            }
        }
    }
}

@Composable
fun ItemTurnoCard(turno: TurnoRiego) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = turno.nombreParcela, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cultivo: ${turno.tipoCultivo} (${turno.hectareas} ha)")
            Text(text = "Hora de inicio: ${turno.horaInicio}")
            Text(
                text = "Estado: ${turno.estado}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}