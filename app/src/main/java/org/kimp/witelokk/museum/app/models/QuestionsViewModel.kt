package org.kimp.witelokk.museum.app.models

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kimp.witelokk.museum.app.R
import org.kimp.witelokk.museum.app.serialization.Question

private data class QuestionsList(
    val questions: List<Question>
)

class QuestionsViewModel : ViewModel() {
    private var questionsList: MutableLiveData<List<Question>> = MutableLiveData(ArrayList())

    fun loadQuestions(resources: Resources) {
        var inputStream = resources.openRawResource(R.raw.questions)

        viewModelScope.launch(Dispatchers.IO) {
            questionsList.postValue(
                GsonBuilder()
                    .create()
                    .fromJson(
                        inputStream.bufferedReader().use { it.readText() },
                        QuestionsList::class.java
                    ).questions
            )
        }
    }

    fun getQuestions() : LiveData<List<Question>> = questionsList
}