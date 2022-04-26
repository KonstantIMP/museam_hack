package org.kimp.witelokk.museum.app.serialization

import com.google.gson.annotations.SerializedName

enum class QuestionType {
    @SerializedName("text")
    TEXT_QUESTION,

    @SerializedName("number")
    NUMBER_QUESTION,

    @SerializedName("qr")
    QR_QUESTION,

    UNKNOWN;
}