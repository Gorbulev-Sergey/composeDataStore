package ru.gorbulevsv.composedatastore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.gorbulevsv.composedatastore.ui.theme.ComposeDataStoreTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.Console

class MainActivity : ComponentActivity() {
    lateinit var dataStoreManager: DataStoreManager
    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataStoreManager = DataStoreManager(this@MainActivity)
        GlobalScope.launch(Dispatchers.IO) {
            dataStoreManager.getFromDataStore().collect {
                name = it.name
                phone = it.phone
                address = it.address
            }
        }

        setContent {
            ComposeDataStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = PhoneBook(name, phone, address).toString())

                            TextField(value = name, onValueChange = { name = it })
                            TextField(value = phone, onValueChange = { phone = it })
                            TextField(value = address, onValueChange = { address = it })

                            Button(onClick = {
                                GlobalScope.launch(Dispatchers.IO) {
                                    dataStoreManager.saveToDataStore(
                                        PhoneBook(
                                            name,
                                            phone,
                                            address
                                        )
                                    )
                                }
                            }) {
                                Text("Сохранить")
                            }
                        }
                    }
                }
            }
        }
    }
}