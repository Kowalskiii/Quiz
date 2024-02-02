sealed class Questions {
    abstract val questionId: String
    abstract val textQuestions: String
}

data class Option(val optionId: String, val optionAnswer: String, val score: Int)

data class SingleChoose(override val questionId: String, override val textQuestions: String, val options : List<Option>): Questions()

data class OpenAnswer(override val questionId: String, override val textQuestions: String): Questions()

