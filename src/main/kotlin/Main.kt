fun main() {
    val employeeId = "kowalski"
    val quizId = "polymath"
    val adminId = "admin"

    val option11 = Option("1.1", "Финляндия", 5)
    val option12 = Option("1.2", "Франция", 0)
    val option13 = Option("1.3", "Ирландия", 0)
    val option14 = Option("1.4", "Швейцария", 0)

    val option21 = Option("2.1", "Ставрополь-на-Волге", 5)
    val option22 = Option("2.2", "Ярославь-на-Волге", 0)
    val option23 = Option("2.3", "Киров-на-Волге", 0)
    val option24 = Option("2.3", "Тамбов-на-Волге", 0)

    val option31 = Option("3.1", "Хром", 0)
    val option32 = Option("3.2", "Вольфрам", 5)
    val option33 = Option("3.3", "Иридий", 0)
    val option34 = Option("3.4", "Молибден", 0)

    val answer1 = SingleChoose(
        "firstQuestion",
        "В какой из нижеперечисленных стран не водятся змеи?",
        listOf(option11, option12, option13, option14)
    )
    val answer2 = SingleChoose(
        "secondQuestion",
        "Как до 1964 года назывался город Тольятти?",
        listOf(option21, option22, option23, option24)
    )
    val answer3 = SingleChoose(
        "fourthQuestion",
        "Какой металл является самым тугоплавким на Земле?",
        listOf(option31, option32, option33, option34)
    )
    val answer4 = OpenAnswer("fifthQuestion", "В какой европейской стране голубей называют «летучими крысами»?")
    val answer5 = OpenAnswer("sixthQuestion", "Какой напиток готовят из сушеных цветков суданской розы?")

    val questionList = listOf(answer1, answer2, answer3, answer4, answer5)

//        val quiz = Quiz(quizId, questionList)
//        val quizProgress = QuizProgress(quiz.quizId, employeeId)

    val result = passQuiz(questionList)


    result.forEach { progress ->
        when (progress) {
            is SingleChooseProgress -> {
                println("Вопрос: ${progress.questionId}, Ответ: ${progress.optionId}, Оценка: ${progress.score}")
            }

            is OpenAnswerProgress -> {
                println("Вопрос: ${progress.questionId}, Ответ: ${progress.answer}, Оценка: ${progress.evaluation}")
            }
        }
    }


}

fun passQuiz(questions: List<Questions>): List<QuizQuestionProgress> {
    println("Вы настоящий эрудит, если сможете ответить верно на 4/5. Тест на эрудицию")


//        val questionProgressList = questions.flatMap { question ->
//            println(question.textQuestions)
//
//            when (question) {
//                is SingleChoose -> {
//                    for (option in question.options) {
//                        println(option.optionAnswer)
//                    }
//                    print("Ваш ответ:")
//                    val answer = readln()
//                    val singleChooseList = getSingleChooseProgress(question.questionId, question.options, answer)
//                    singleChooseList
//
//                }
//                is OpenAnswer -> {
//                    print("Ваш ответ:")
//                    val answer = readln()
//                    if (answer != null) {
//                        val openAnswer = getOpenAnswerProgress(question.questionId, answer, null)
//                        listOf(openAnswer)
//                    } else listOf()
//                }
//            }

    val questionProgressList = buildList {
        for (question in questions) {
            println(question.textQuestions)

            when (question) {
                is SingleChoose -> {
                    for (option in question.options) {
                        println(option.optionAnswer)
                    }
                    print("Ваш ответ:")
                    val answer = readln()
                    val singleChooseList = getSingleChooseProgress(question.questionId, question.options, answer)
                    addAll(singleChooseList)

                }

                is OpenAnswer -> {
                    print("Ваш ответ:")
//                    runCatching { readln() }.getOrNull()?.let { answer ->
//                        val openAnswer = getOpenAnswerProgress(question.questionId, answer, null)
//                        add(openAnswer)
//                    }
                    readlnOrNull()?.let { answer ->
                        val openAnswer = getOpenAnswerProgress(question.questionId, answer, null)
                        add(openAnswer)
                    }
                }
            }
        }
    }
    return questionProgressList
}

fun getSingleChooseProgress(questionId: String, options: List<Option>, answer: String): List<QuizQuestionProgress> {
    val answerList: List<String> = answer.split(",").map { it.trim() }
    val singleChooseList: MutableList<QuizQuestionProgress> = mutableListOf()

    for (answer in answerList) {
        val option = options.find { it.optionAnswer == answer }
        if (option != null) {
            val singleChooseProgress = SingleChooseProgress(questionId, option.optionId, option.score)
            singleChooseList.add(singleChooseProgress)
        }
    }

    return singleChooseList
}

fun getOpenAnswerProgress(questionId: String, answer: String, evaluation: QuizAnswerEvaluation?): QuizQuestionProgress {
    return OpenAnswerProgress(questionId, answer, evaluation)
}


