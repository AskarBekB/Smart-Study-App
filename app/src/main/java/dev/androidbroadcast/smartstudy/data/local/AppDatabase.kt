package dev.androidbroadcast.smartstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.androidbroadcast.smartstudy.domain.model.Session
import dev.androidbroadcast.smartstudy.domain.model.Subject
import dev.androidbroadcast.smartstudy.domain.model.Task

@Database(
    entities = [Subject::class, Session::class, Task::class],
    version = 1
)

@TypeConverters(ColorListProvider::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun sessionDao(): SessionDao
}