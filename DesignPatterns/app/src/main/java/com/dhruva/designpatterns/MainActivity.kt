package com.dhruva.designpatterns

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.designpatterns.creational.BuilderDesignPattern
import com.dhruva.designpatterns.creational.FactoryDesignPattern
import com.dhruva.designpatterns.creational.SingletonDesignPattern
import com.dhruva.designpatterns.ui.theme.DesignPatternsTheme
import kotlin.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DesignPatternsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DesignPatternsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DesignPatternsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonComponent(
            onClick = {
                // Access it directly by name—no need to call a constructor
                SingletonDesignPattern.testSingletonDesignPattern()
                // Every reference points to the same instance
                val instance1 = SingletonDesignPattern
                val instance2 = SingletonDesignPattern

                println(buildString {
                    append("are instance same? ")
                    append((instance2 == instance1)) //output : true
                })
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Singleton"
        )

        ButtonComponent(
            onClick = {
                // The client doesn't need to know about JsonFileParser or XmlFileParser directly
                val parser = FactoryDesignPattern.createParser("xml")
                // Output: Parsing data from a JSON file...
                println(parser.parse())
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Factory"
        )

        ButtonComponent(
            onClick = {
                val car = BuilderDesignPattern.Car.Builder()
                    .setBrand("Tesla")
                    .setModel("Model 3")
                    .setYear(2024)
                    .setSunroof(true)
                    .build()
                print(car.print())
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Builder"
        )

    }
}

@Composable
fun ButtonComponent(onClick: () -> Unit, buttonText: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = buttonText
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DesignPatternsScreenPreview() {
    DesignPatternsTheme {
        DesignPatternsScreen()
    }
}



