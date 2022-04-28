package org.kimp.witelokk.museum.app.ui.fragments

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.synnapps.carouselview.ViewListener
import org.kimp.witelokk.museum.app.databinding.FragmentQuestBinding
import org.kimp.witelokk.museum.app.databinding.ViewQuestionBinding
import org.kimp.witelokk.museum.app.models.QuestionsViewModel
import org.kimp.witelokk.museum.app.serialization.QuestionType

class QuestFragment : Fragment() {
    private lateinit var questionsViewModel: QuestionsViewModel
    private lateinit var binding: FragmentQuestBinding

    @SuppressLint("SetTextI18n")
    private val questionViewListener = ViewListener {
        val questionView = ViewQuestionBinding.inflate(
            layoutInflater
        )

        questionView.qvNumberMsg.text = "${it + 1} / ${questionsViewModel.getQuestions().value!!.size}"

        val question = questionsViewModel.getQuestions().value!![it]

        if (question.image == "") questionView.qvQuestionImage.visibility = View.GONE
        questionView.qvQuestionTextMsg.text = "\t" + question.text

        if (question.type != QuestionType.QR_QUESTION) questionView.qvScanQrBtn.visibility = View.GONE
        if (question.type != QuestionType.TEXT_QUESTION) questionView.qvTextAnswerIl.visibility = View.GONE
        if (question.type != QuestionType.NUMBER_QUESTION) questionView.qvNumberAnswerIl.visibility = View.GONE

        if (question.type == QuestionType.QR_QUESTION) questionView.qvCheckBtn.visibility = View.GONE

        questionView.root
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)

        questionsViewModel = ViewModelProvider(this)[QuestionsViewModel::class.java]
        questionsViewModel.loadQuestions(resources)
        questionsViewModel.getQuestions().observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                Log.e("ViewDebug", "Something ${it.size}")

                binding.qfQuestionsCarousel.setViewListener(questionViewListener)
                binding.qfQuestionsCarousel.pageCount = it.size

            }
        }

        return binding.root
    }
}