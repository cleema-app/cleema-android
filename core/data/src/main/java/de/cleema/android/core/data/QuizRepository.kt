package de.cleema.android.core.data

import kotlinx.coroutines.flow.Flow
import java.util.*

interface QuizRepository {
    fun getQuizStream(): Flow<Result<de.cleema.android.core.models.QuizState>>
    suspend fun answerQuiz(id: UUID, choice: de.cleema.android.core.models.Quiz.Choice)
}
