package dev.androidbroadcast.smartstudy.domain.repository

import dev.androidbroadcast.smartstudy.domain.model.Book

interface BookRepository {
    suspend fun getStudyBooks(): List<Book>
}