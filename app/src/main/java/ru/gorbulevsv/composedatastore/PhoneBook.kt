package ru.gorbulevsv.composedatastore

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class PhoneBook(var name: String = "", var phone: String = "", var address: String = "")