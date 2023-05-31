package com.example.swipetorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.swipetorefresh.ui.theme.SwipeToRefreshTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToRefreshTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val refreshScope = rememberCoroutineScope()
                    var refreshing by remember { mutableStateOf(false) }
                    var itemCount by remember { mutableStateOf(15) }

                    fun refresh() = refreshScope.launch {
                        refreshing = true
                        delay(1500)
                        itemCount += 5
                        refreshing = false
                    }

                    val state = rememberPullRefreshState(refreshing, ::refresh)

                    Box(Modifier.pullRefresh(state)) {

                        //vertically scrollable content
                        LazyColumn(Modifier.fillMaxSize()) {
                            if (!refreshing) {
                                items(itemCount) {
                                    ListItem {
                                        Text(text = "Item ${itemCount - it}")
                                    }
                                }
                            }
                        }

                        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
                    }
                }
            }
        }
    }
}
