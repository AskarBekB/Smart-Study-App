package dev.androidbroadcast.smartstudy.presentation.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookScreen(
    navigator: DestinationsNavigator,
    viewModel: BookViewModel = hiltViewModel()
) {
    val books = viewModel.books.value
    val loading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Books") },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }

                errorMessage != null -> {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn {
                        items(books) { book ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Row(modifier = Modifier.padding(12.dp)) {
                                    if (book.thumbnail.isNotEmpty()) {
                                        Image(
                                            painter = rememberAsyncImagePainter(book.thumbnail),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(64.dp)
                                                .padding(end = 12.dp)
                                        )
                                    }
                                    Column {
                                        Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                                        Text(text = book.authors, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
