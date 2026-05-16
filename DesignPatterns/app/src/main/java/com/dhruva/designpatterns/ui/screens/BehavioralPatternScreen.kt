package com.dhruva.designpatterns.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dhruva.designpatterns.behavioural.AnalyticsVisitor
import com.dhruva.designpatterns.behavioural.BubbleSort
import com.dhruva.designpatterns.behavioural.Button
import com.dhruva.designpatterns.behavioural.Chanel
import com.dhruva.designpatterns.behavioural.CustomerService
import com.dhruva.designpatterns.behavioural.DataDisplay
import com.dhruva.designpatterns.behavioural.EditorCoordinator
import com.dhruva.designpatterns.behavioural.Image
import com.dhruva.designpatterns.behavioural.LogsInvoker
import com.dhruva.designpatterns.behavioural.Manager
import com.dhruva.designpatterns.behavioural.MusicPlayer
import com.dhruva.designpatterns.behavioural.PrintLog
import com.dhruva.designpatterns.behavioural.QuickSort
import com.dhruva.designpatterns.behavioural.Request
import com.dhruva.designpatterns.behavioural.Sorter
import com.dhruva.designpatterns.behavioural.Supervisor
import com.dhruva.designpatterns.creational.SingletonDesignPattern
import com.dhruva.designpatterns.showToast
import com.dhruva.designpatterns.ui.components.ButtonComponent

@Composable
fun BehavioralPatternScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Observer design pattern
        ButtonComponent(
            onClick = {
                //register to the chanel and get updated data
                val chanel = Chanel()
                var num = 0
                val dataDisplay = DataDisplay()
                chanel.register(dataDisplay)
                chanel.setData(num++.toString())

                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Observer"
        )

        //Command design pattern
        ButtonComponent(
            onClick = {
                val logsInvoker = LogsInvoker()
                logsInvoker.addLog(PrintLog("this is log number 1 "))
                logsInvoker.addLog(PrintLog("this is log number 2 "))
                logsInvoker.addLog(PrintLog("this is log number 3 "))
                logsInvoker.executeLogs()
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Command"
        )

        //Chain of responsibility design pattern
        ButtonComponent(
            onClick = {
                //create chain of responsibility
                val manager = Manager()
                val supervisor = Supervisor(manager)
                val customerSupport = CustomerService(supervisor)

                val request = Request("Complaint")
                //pass the request through chain
                customerSupport.handleRequest(request)

                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Chain Of Responsibility"
        )

        //Strategy design pattern
        ButtonComponent(
            onClick = {
                val dataset = mutableListOf(9, 2, 3, 7, 5, 6)

                // Using Bubble Sort
                val sorter = Sorter(BubbleSort())
                sorter.sort(dataset.toMutableList())

                // Using Quick Sort
                sorter.setStrategy(QuickSort())
                sorter.sort(dataset.toMutableList())

                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Strategy"
        )

        //State design pattern
        ButtonComponent(
            onClick = {
                val musicPlayer = MusicPlayer()
                musicPlayer.pressPlay() //output : already paused, will play now
                musicPlayer.pressPlay() //output : already playing, will pause now
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "State"
        )

        //Visitor design pattern
        ButtonComponent(
            onClick = {
                val uiElements = listOf(
                    Button("btn_1", "Login"),
                    Image("https://example.com")
                )
                val analyticsVisitor = AnalyticsVisitor()

                uiElements.forEach { it.accept(analyticsVisitor) }
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Visitor"
        )

        //Memento design pattern
        ButtonComponent(
            onClick = {
                val screenContext = EditorCoordinator()

                // Simulate user behavior
                screenContext.writeText("Hello")
                screenContext.writeText("Hello World")
                screenContext.writeText("Hello World! Welcome to Kotlin.")
                screenContext.printCurrentText()

                println("\n--- Initializing Undos ---")
                screenContext.triggerUndo() // Steps back to: "Hello World"
                screenContext.triggerUndo() // Steps back to: "Hello"
                screenContext.triggerUndo() // Steps back to empty string ""
                screenContext.triggerUndo() // Fails gracefully: No history left

                println("\n--- Final Check ---")
                screenContext.printCurrentText()
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Memento"
        )
    }
}