package dev.androidbroadcast.smartstudy.presentation.book

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.androidbroadcast.smartstudy.domain.model.Book
import dev.androidbroadcast.smartstudy.domain.repository.BookRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repo: BookRepository
): ViewModel() {

    var books = mutableStateOf<List<Book>>(emptyList())
        private set

    var isLoading = mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            books.value = repo.getStudyBooks()
            isLoading.value = false
        }
    }
}