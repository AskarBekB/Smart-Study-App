package dev.androidbroadcast.smartstudy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.androidbroadcast.smartstudy.data.remote.BookApi
import dev.androidbroadcast.smartstudy.data.repository.BookRepositoryImpl
import dev.androidbroadcast.smartstudy.domain.repository.BookRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BookApi =
        retrofit.create(BookApi::class.java)

    @Provides
    @Singleton
    fun provideBookRepository(
        api: BookApi
    ): BookRepository = BookRepositoryImpl(api)
}
