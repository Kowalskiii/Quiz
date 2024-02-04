
fun main() {
    val employeeId =  "kowalski"
    val adminId = "admin"

    val quizId = "polymath"
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

    val question1 = SingleChoose("firstQuestion", "В какой из нижеперечисленных стран не водятся змеи?", listOf(option11, option12, option13, option14))
    val question2 = SingleChoose("secondQuestion", "Как до 1964 года назывался город Тольятти?", listOf(option21, option22, option23, option24))
    val question3= SingleChoose("thirdQuestion", "Какой металл является самым тугоплавким на Земле?", listOf(option31, option32, option33, option34))
    val question4 = OpenAnswer("fourthQuestion", "В какой европейской стране голубей называют «летучими крысами»?")
    val question5 = OpenAnswer("fifthQuestion", "Какой напиток готовят из сушеных цветков суданской розы?")

    val questionList = listOf(question1, question2, question3, question4, question5)

    val quiz = Quiz(quizId, questionList)

    val quizQuestionProgress = passQuiz(quiz.questions)

    quizQuestionProgress.forEach { progress ->
        when (progress) {
            is SingleChooseProgress -> {
                println("Вопрос: ${progress.questionId}, Ответ: ${progress.optionId}, Оценка: ${progress.score}")
            }
            is OpenAnswerProgress -> {
                println("Вопрос: ${progress.questionId}, Ответ: ${progress.answer}, Оценка: ${progress.evaluation}")
            }
        }
    }

    var globalProgress = QuizProgress(quizProgressId = "1", employeeId, quiz.quizId, quizQuestionProgress)

    val quizAnswerEvaluation = getQuizProgress(quiz, globalProgress, adminId)

    var sumScore = 0



    quizAnswerEvaluation.questionsProgress.forEach { progress ->
        when(progress) {
            is SingleChooseProgress -> sumScore += progress.score
            is OpenAnswerProgress -> {
                val sumScoreOpenAnswer = progress.evaluation?.score ?: 0
                sumScore += sumScoreOpenAnswer
            }
        }

    }

    println("Результат за тест: ${sumScore} баллов")

}


fun passQuiz(questions: List<Questions>) : List<QuizQuestionProgress>{
    println("Вы настоящий эрудит, если сможете ответить верно на 4/5. Тест на эрудицию")

    val questionProgressList = buildList {
        for (question in questions) {
            println("\n${question.textQuestions}")

            when (question) {
                is SingleChoose -> {
                    for (option in question.options) {
                        println(option.optionAnswer)
                    }
                    print("Ваш ответ:")
                    readlnOrNull()?.let { answer ->
                        val singleChooseList =
                            getSingleChooseProgress(question.questionId, question.options, answer)
                        addAll(singleChooseList)
                    }
                }
                is OpenAnswer -> {
                    print("Ваш ответ:")
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

    val singleChooseList = buildList {
        for (answer in answerList) {
            val option = options.find { it.optionAnswer == answer }
            if (option != null) {
                val singleChooseProgress = SingleChooseProgress(questionId, option.optionId, option.score)
                add(singleChooseProgress)
            }
        }
    }
    return singleChooseList
}


fun getOpenAnswerProgress(questionId: String, answer: String, evaluation: QuizAnswerEvaluation? ): QuizQuestionProgress {
    return OpenAnswerProgress(questionId, answer, evaluation)
}


fun getQuizProgress(quiz: Quiz, quiz_progress: QuizProgress, adminId: String) : QuizProgress {

    var finalQuizProgress = quiz_progress

    val progressMap = quiz_progress.questionsProgress.associateBy { it.questionId }


    quiz.questions.forEach { question ->
        if (question is OpenAnswer) {
            val questionProgress = progressMap[question.questionId] as OpenAnswerProgress

            println("\nОцените ответы на вопросы по 5-балльной шкале")
            println(
                """                                                                                                                                                                                                                                                 
                    | Вопрос:                                                                                                                                                                                                                                       
                    |        ${question.textQuestions}                                                                                                                                                                                                              
                    | Ответ:                                                                                                                                                                                                                                        
                    |       ${questionProgress.answer}                                                                                                                                                                                                              
                    |Оценка за ответ:                                                                                                                                                                                                                               
                """.trimIndent().trimMargin("|")
            )

            readlnOrNull()?.let { adminScore ->
                val updatedQuestionProgress = questionProgress.copy(
                    evaluation = adminScore.toIntOrNull()?.let { QuizAnswerEvaluation(adminId, it) }
                )

                finalQuizProgress = finalQuizProgress.copy(
                    questionsProgress = finalQuizProgress.questionsProgress.map {
                        if (it.questionId == updatedQuestionProgress.questionId) {
                            updatedQuestionProgress
                        } else {
                            it
                        }
                    }
                )
            }
        }

    }

    return finalQuizProgress
}                                                                                                                                                                                                                                                                   
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                    