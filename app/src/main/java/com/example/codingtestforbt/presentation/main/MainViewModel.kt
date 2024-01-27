package com.example.codingtestforbt.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.codingtestforbt.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): ViewModel() {

    private val _startDestination = mutableStateOf(Route.NewsListScreen.route)
    val startDestination: State<String> = _startDestination
}




