package dev.androidbroadcast.smartstudy.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.androidbroadcast.smartstudy.presentation.theme.gradient1
import dev.androidbroadcast.smartstudy.presentation.theme.gradient2
import dev.androidbroadcast.smartstudy.presentation.theme.gradient3
import dev.androidbroadcast.smartstudy.presentation.theme.gradient4
import dev.androidbroadcast.smartstudy.presentation.theme.gradient5

@Entity
data class Subject(
    val name: String,
    val goalHours: Float,
    val colors: List<Color>,
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int? = null
) {

    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
