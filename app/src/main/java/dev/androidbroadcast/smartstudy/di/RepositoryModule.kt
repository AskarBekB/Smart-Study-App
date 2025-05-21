package dev.androidbroadcast.smartstudy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.androidbroadcast.smartstudy.data.repository.SessionRepositoryImpl
import dev.androidbroadcast.smartstudy.data.repository.SubjectRepositoryImpl
import dev.androidbroadcast.smartstudy.data.repository.TaskRepositoryImpl
import dev.androidbroadcast.smartstudy.domain.repository.SessionRepository
import dev.androidbroadcast.smartstudy.domain.repository.SubjectRepository
import dev.androidbroadcast.smartstudy.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}

