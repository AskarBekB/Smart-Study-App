package dev.androidbroadcast.smartstudy.presentation.session

import dev.androidbroadcast.smartstudy.domain.model.Session
import dev.androidbroadcast.smartstudy.domain.model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)