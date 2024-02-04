package db

import Quiz

object QuizDB {
    private val quizzes = mutableMapOf<String, Quiz>()

    fun getQuizById(id: String): Quiz? = quizzes[id]

    fun createQuiz(quiz: Quiz) {
        if (quizzes.contains(quiz.quizId)) throw RuntimeException("Id duplicate")
        quizzes[quiz.quizId] = quiz
    }

    fun updateQuiz(quiz: Quiz) {
        if (!quizzes.contains(quiz.quizId)) throw RuntimeException("Nothing to update")
        quizzes[quiz.quizId] = quiz
    }

    fun deleteQuizById(id: String) {
        quizzes.remove(id)
    }
}