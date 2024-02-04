package db
import Quiz
import java.lang.RuntimeException

object QuizDB {

    private val quizzes = mutableMapOf<String, Quiz>()

    fun getQuizById(id: String) : Quiz? = quizzes[id]

    fun createQuiz(quiz: Quiz){
        if (quizzes.contains(quiz.quizId)) throw RuntimeException("Id duplicate")
        quizzes[quiz.quizId] = quiz
    }

    fun update(quiz: Quiz){
        if(!quizzes.contains(quiz.quizId)) throw RuntimeException("Nothing to update")
        quizzes[quiz.quizId] = quiz
    }

    fun deleteById(id:String){
        quizzes.remove(id)
    }

}