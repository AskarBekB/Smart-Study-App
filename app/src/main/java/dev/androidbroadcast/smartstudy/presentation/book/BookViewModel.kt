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

    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                books.value = repo.getStudyBooks()
            } catch (e: Exception) {
                errorMessage.value = "Please! Check you internet connection"
            } finally {
                isLoading.value = false
            }
        }
    }
}