package db

import QuizProgress
import java.lang.RuntimeException

object QuizProgressDB {
    private val quizzesProgresses = mutableMapOf<String, QuizProgress>()

    fun getQuizProgressById(id: String) : QuizProgress? = quizzesProgresses[id]

    fun createQuizProgress(quizProgress: QuizProgress){
        if(quizzesProgresses.contains(quizProgress.quizProgressId)) throw RuntimeException ("Id duplicate")
        quizzesProgresses[quizProgress.quizProgressId] = quizProgress
    }

    fun updateQuizProgress(quizProgress: QuizProgress){
        if(!quizzesProgresses.contains(quizProgress.quizProgressId)) throw RuntimeException("Nothing to update")
        quizzesProgresses[quizProgress.quizProgressId] = quizProgress

    }

    fun deleteQuizProgressById(id: String){
        quizzesProgresses.remove(id)
    }

}