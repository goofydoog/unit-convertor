package com.example.unitconverter

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun UnitConverter(){

    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var outputUnitForResult by remember { mutableStateOf("") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableDoubleStateOf(1.00) }
    val oconversionFactor = remember { mutableDoubleStateOf(1.00) }
    val keyboardController = LocalSoftwareKeyboardController.current


    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = BigDecimal(inputValueDouble * conversionFactor.doubleValue / oconversionFactor.doubleValue)
            .setScale(2, RoundingMode.HALF_EVEN)
        outputValue = result.toString()
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        


        Text("Unit Converter", color = Color.Red, fontSize = 30.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue, onValueChange = {

                if(it.all {char -> char.isDigit() &&
                        it.length <= 10  })
                    inputValue = it
                convertUnits()
                if (it.isNotEmpty())
                    outputUnitForResult = outputUnit
                else if (it.isEmpty())
                    outputUnitForResult = ""
        },
            label = { Text(text = "Enter Value") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Box {
                Button(onClick = {
                    keyboardController?.hide()
                    iExpanded = true
                    }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown,contentDescription = "Arrow Down")
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false}) {
                    DropdownMenuItem(
                        text = {Text("Centimetres")},
                        onClick = {
                            inputUnit = "Centimetres"
                            iExpanded = false
                            conversionFactor.doubleValue = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Meters")},
                        onClick = {
                            inputUnit = "Meters"
                            iExpanded = false
                            conversionFactor.doubleValue = 1.0
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Millimetres")},
                        onClick = {
                            inputUnit = "Millimetres"
                            iExpanded = false
                            conversionFactor.doubleValue = 0.001
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Feet")},
                        onClick = {
                            inputUnit = "Feet"
                            iExpanded = false
                            conversionFactor.doubleValue = 0.3048
                            convertUnits()
                        })
                }
            }

            Spacer(modifier = Modifier.width(50.dp))
            Box {
                Button(onClick = {
                    keyboardController?.hide()
                    oExpanded = true
                }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown,contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false}) {
                    DropdownMenuItem(
                        text = {Text("Centimetres")},
                        onClick = {
                            outputUnit = "Centimetres"
                            oExpanded = false
                            oconversionFactor.doubleValue = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Meters")},
                        onClick = {
                            outputUnit = "Meters"
                            oExpanded = false
                            oconversionFactor.doubleValue = 1.0
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Millimetres")},
                        onClick = {
                            outputUnit = "Millimetres"
                            oExpanded = false
                            oconversionFactor.doubleValue = 0.001
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = {Text("Feet")},
                        onClick = {
                            outputUnit = "Feet"
                            oExpanded = false
                            oconversionFactor.doubleValue = 0.3048
                            convertUnits()
                        })

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "Result $outputValue $outputUnitForResult",
                style = MaterialTheme.typography.headlineMedium)
        }
        
        
        Spacer(modifier = Modifier.height(100.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}

