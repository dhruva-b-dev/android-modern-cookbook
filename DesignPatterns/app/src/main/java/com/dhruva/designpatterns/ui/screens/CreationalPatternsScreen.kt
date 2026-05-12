package com.dhruva.designpatterns.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.designpatterns.creational.BuilderDesignPattern
import com.dhruva.designpatterns.creational.FactoryDesignPattern
import com.dhruva.designpatterns.creational.HomeTheaterFacade
import com.dhruva.designpatterns.creational.Lights
import com.dhruva.designpatterns.creational.Projector
import com.dhruva.designpatterns.creational.Remote
import com.dhruva.designpatterns.creational.SingletonDesignPattern
import com.dhruva.designpatterns.creational.SoundSystem
import com.dhruva.designpatterns.creational.Tv
import com.dhruva.designpatterns.showToast
import com.dhruva.designpatterns.ui.components.ButtonComponent

@Composable
fun CreationalPatternsScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Singleton Design Pattern
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

        //Factory Design Pattern
        ButtonComponent(
            onClick = {
                // The client doesn't need to know about JsonFileParser or XmlFileParser directly
                val parser = FactoryDesignPattern.createParser("json")
                // Output: Parsing data from a JSON file...
                println(parser.parse())
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Factory"
        )

        //Builder Design Pattern
        ButtonComponent(
            onClick = {
                val car = BuilderDesignPattern.Car.Builder()
                    .setBrand("Tesla")
                    .setModel("Model 3")
                    .setYear(2024)
                    .setSunroof(true)
                    .build()
                println(car.print())
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Builder"
        )

        //Facade Design Pattern
        ButtonComponent(
            onClick = {
                val homeTheaterFacade = HomeTheaterFacade(
                    projector = Projector(),
                    soundSystem = SoundSystem(),
                    lights = Lights()
                )
                homeTheaterFacade.watchMovie("Life Of Pi")
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Facade"
        )

        //Dependency Injection
        ButtonComponent(
            onClick = {
                //create object of external dependency
                val remote = Remote()
                //inject external dependency via constructor
                val tv = Tv(remote = remote)
                tv.start()
                context.showToast(text = "Created!", Toast.LENGTH_SHORT)
            },
            buttonText = "Dependency Injection"
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CreationalPatternsScreenPreview() {
    CreationalPatternsScreen()
}