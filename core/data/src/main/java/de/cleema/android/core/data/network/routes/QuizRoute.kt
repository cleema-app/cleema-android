package de.cleema.android.core.data.network.routes

import de.cleema.android.core.data.network.requests.AnswerQuizRequest
import de.cleema.android.core.data.network.requests.ApiRequest
import de.cleema.android.core.data.network.responses.AnswerQuizResponse
import de.cleema.android.core.data.network.responses.ApiResponse
import de.cleema.android.core.data.network.responses.QuizResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface QuizRoute {
    @GET("/api/quizzes/current")
    suspend fun getQuiz(): Result<ApiResponse<QuizResponse>>

    @POST("/api/quiz-responses")
    suspend fun answerQuiz(@Body request: ApiRequest<AnswerQuizRequest>): Result<ApiResponse<AnswerQuizResponse>>
}
