package org.kimp.witelokk.museum.app.serialization

data class Question(
    val number: Int,
    val image: String = "",
    val text: String,
    val type: QuestionType = QuestionType.UNKNOWN,
    val correctAnswer: String,
    var givenAnswer: String = ""
)
