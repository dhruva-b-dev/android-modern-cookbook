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
import com.dhruva.designpatterns.showToast
import com.dhruva.designpatterns.structural.CachingDecorator
import com.dhruva.designpatterns.structural.Directory
import com.dhruva.designpatterns.structural.FeatureProxy
import com.dhruva.designpatterns.structural.File
import com.dhruva.designpatterns.structural.LoggingDecorator
import com.dhruva.designpatterns.structural.MicroUsb
import com.dhruva.designpatterns.structural.MicroUsbToUsbAdapter
import com.dhruva.designpatterns.structural.SimpleDataService
import com.dhruva.designpatterns.structural.User
import com.dhruva.designpatterns.ui.components.ButtonComponent

@Composable
fun StructuralPatternsScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Adapter Design Pattern
        ButtonComponent(
            onClick = {
                //legacy device
                val microUsb = MicroUsb()
                //client wants the Usb Type, so we use adapter
                val microUsbAdapter = MicroUsbToUsbAdapter(microUsb)
                //use the Usb method
                microUsbAdapter.connectWithUsb()
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Adapter"
        )

        //Decorator design pattern
        ButtonComponent(
            onClick = {
                val basicService = SimpleDataService()
                val loggingService = LoggingDecorator(basicService)
                val cachingService = CachingDecorator(loggingService)
                //so, this will have both logged and cached data.
                println(cachingService.fetchData())
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Decorator"
        )
        //Composite design pattern
        ButtonComponent(
            onClick = {
                val myFile = File("my_file")
                val subDirectory = Directory("my_directory")
                subDirectory.add(File("app.kt"))

                val rootDirectory = Directory("root_dir")
                rootDirectory.add(myFile)
                rootDirectory.add(subDirectory)

                rootDirectory.showDetails()
                context.showToast(text = "Done!", Toast.LENGTH_SHORT)
            },
            buttonText = "Composite"
        )

        //Protection Proxy design pattern
        ButtonComponent(
            onClick = {
                val premiumFeature = FeatureProxy(User(name = "test1", isSubscribed = true))
                premiumFeature.open()  //output : Access Granted

                val basicFeature = FeatureProxy(User(name = "test2", isSubscribed = false))
                basicFeature.open() //output : Access Denied
            },
            buttonText = "Protection Proxy"
        )
    }
}