sealed class QuizQuestionProgress{
    abstract val questionId: String
}

class QuizAnswerEvaluation(val adminId: String, val score:Int)

data class SingleChooseProgress(override  val questionId: String, val optionId: String, val score: Int): QuizQuestionProgress()
data class OpenAnswerProgress(override val questionId: String, val answer: String, val evaluation: QuizAnswerEvaluation?): QuizQuestionProgress()

data class QuizProgress(
    val quizProgressId: String,
    val employeeId: String,
    val quizId: String,
    val questionsProgress: List<QuizQuestionProgress>
)
