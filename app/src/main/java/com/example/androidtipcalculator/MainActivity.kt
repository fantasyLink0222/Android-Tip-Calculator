package com.example.androidtipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtipcalculator.ui.theme.AndroidTipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount,tipPercent)
    val total = calculateTotal(amount, tipPercent)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(onClick = {  serviceCostAmountInput= (1..999999).random().toString() })
        {
            Text(stringResource(R.string.generate_random_bill))
            
        }

        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Column (
            modifier = Modifier.align(Alignment.CenterHorizontally))
           {
            Row {
                Button(onClick = { tipPercent = 10.0 }, Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.ten_percent))
                }
                Button(onClick = { tipPercent = 15.0 }, Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.fifteen_percent))
                }
            }
            Row {
                Button(onClick = { tipPercent = 18.0 }, Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.eighteen_percent))
                }
                Button(onClick = { tipPercent = 20.0 }, Modifier.padding(top = 8.dp)) {
                    Text(stringResource(R.string.twenty_percent))
                }
            }
        }

        Text("Current Tip: $tipPercent%")

        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.bill_amount, amount),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.total, total),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditServiceCostField(
value: String,
onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

}


private fun calculateTip(
    amount: Double,
    tipPercent: Double
): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

private fun calculateTotal(
    amount: Double,
    tipPercent: Double
): String {
    val total = tipPercent / 100 * amount+amount
    return NumberFormat.getCurrencyInstance().format(total)
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTipCalculatorTheme {
        TipCalculatorScreen()
    }
}