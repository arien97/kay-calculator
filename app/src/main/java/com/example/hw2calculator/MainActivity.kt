package com.example.hw2calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hw2calculator.ui.theme.HW2CalculatorTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator()
        }
    }
}

@Composable
fun Calculator() { 
    var input by remember {mutableStateOf("")}
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {

        TextField(
            value = input,
            onValueChange = { newInput ->
                // Only allow valid characters
                if (newInput.matches(Regex("^[0-9+\\-*/. ]*$"))) {
                    input = newInput
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(text = "Result: $result", modifier = Modifier.padding(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appendToInput("1", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "1")
            }
            Button(onClick = { appendToInput("2", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "2")
            }
            Button(onClick = { appendToInput("3", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "3")
            }
            Button(onClick = { appendToInput("+", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "+")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appendToInput("4", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "4")
            }
            Button(onClick = { appendToInput("5", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "5")
            }
            Button(onClick = { appendToInput("6", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "6")
            }
            Button(onClick = { appendToInput("-", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "-")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appendToInput("7", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "7")
            }
            Button(onClick = { appendToInput("8", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "8")
            }
            Button(onClick = { appendToInput("9", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "9")
            }
            Button(onClick = { appendToInput("*", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "*")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appendToInput("0", input) { input = it } }, modifier = Modifier.weight(2f)) {
                Text(text = "0")
            }
            Button(onClick = { appendToInput(".", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = ".")
            }
            Button(onClick = {
                result = evaluateExpression(input)
            }, modifier = Modifier.weight(2f)) {
                Text(text = "=")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appendToInput("/", input) { input = it } }, modifier = Modifier.weight(1f)) {
                Text(text = "/")
            }
            Button(onClick = {
                input = ""
                result = ""
            }, modifier = Modifier.weight(1f)) {
                Text(text = "C")
            }

        }
    }
}

fun appendToInput(value: String, input: String, updateInput: (String) -> Unit) {
    updateInput(input + value)
}

fun evaluateExpression(expression: String): String {
    return try {
        val result = calculate(expression)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun calculate(expression: String): Double {
    val sanitizedExpression = expression.replace(" ", "")
    val operators: List<Char> = listOf('+', '-', '*', '/')

    var total = 0.0
    var currentOperation = '+'
    var currentNumber = ""

    for (char in sanitizedExpression) {
        if (char in '0'..'9' || char == '.') {
            currentNumber += char
        } else if (char in operators) {
            if (currentNumber.isNotEmpty()) {
                total = performOperation(total, currentNumber.toDouble(), currentOperation)
                currentNumber = ""
            }
            currentOperation = char
        }
    }

    // Process the last number
    if (currentNumber.isNotEmpty()) {
        total = performOperation(total, currentNumber.toDouble(), currentOperation)
    }

    return total
}

fun performOperation(total: Double, number: Double, operation: Char): Double {
    return when (operation) {
        '+' -> total + number
        '-' -> total - number
        '*' -> total * number
        '/' -> {
            if (number == 0.0) throw ArithmeticException("Division by zero")
            total / number
        }
        else -> total
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    Calculator()
}