package dev.androidbroadcast.smartstudy.data.repository

import dev.androidbroadcast.smartstudy.data.remote.BookApi
import dev.androidbroadcast.smartstudy.domain.model.Book
import dev.androidbroadcast.smartstudy.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi
): BookRepository {
    override suspend fun getStudyBooks(): List<Book> {
        return api.getBooksBySubject().items.map { item ->
            Book(
                id = item.id,
                title = item.volumeInfo.title,
                authors = item.volumeInfo.authors.joinToString(", "),
                thumbnail = item.volumeInfo.imageLinks?.thumbnail
                    ?.replace("http://", "https://")
                    .orEmpty()
            )
        }
    }
}