package com.example.agroriego.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.agroriego.R
import com.example.agroriego.ui.viewmodel.RiegoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RiegoViewModel,
    onTurnoGuardado: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val opcionesCultivo = listOf(
        stringResource(R.string.crop_corn),
        stringResource(R.string.crop_sorghum),
        stringResource(R.string.crop_vegetables)
    )

    var mostrarTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.title_register),
            style = MaterialTheme.typography.headlineMedium
        )

        // Caja de Texto: Nombre de Parcela
        OutlinedTextField(
            value = uiState.nombreParcelaInput,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text(stringResource(R.string.label_parcel_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Caja de Texto: Hectáreas
        OutlinedTextField(
            value = uiState.hectareasInput,
            onValueChange = { viewModel.onHectareasChange(it) },
            label = { Text(stringResource(R.string.label_area)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Botones de Opción (RadioButtons)
        Text(
            text = stringResource(R.string.label_crop_type),
            style = MaterialTheme.typography.titleMedium
        )

        opcionesCultivo.forEach { cultivo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (cultivo == uiState.cultivoSeleccionado),
                        onClick = { viewModel.onCultivoSelected(cultivo) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (cultivo == uiState.cultivoSeleccionado),
                    onClick = { viewModel.onCultivoSelected(cultivo) }
                )
                Text(text = cultivo, modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Selector de Hora (TimePicker)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${stringResource(R.string.label_time_picker)}: ${uiState.horaInicioSeleccionada}")
            Button(onClick = { mostrarTimePicker = true }) {
                Text(stringResource(R.string.btn_select_time))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón Guardar
        Button(
            onClick = {
                viewModel.guardarTurno()
                onTurnoGuardado()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_save))
        }
    }

    // Diálogo emergente para el TimePicker
    if (mostrarTimePicker) {
        val timePickerState = rememberTimePickerState(initialHour = 8, initialMinute = 0)
        AlertDialog(
            onDismissRequest = { mostrarTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val horaFormateada = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    viewModel.onHoraSelected(horaFormateada)
                    mostrarTimePicker = false
                }) {
                    Text("OK")
                }
            },
            text = { TimePicker(state = timePickerState) }
        )
    }
}