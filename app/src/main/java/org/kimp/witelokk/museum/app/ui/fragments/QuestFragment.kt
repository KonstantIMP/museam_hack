package org.kimp.witelokk.museum.app.ui.fragments

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ViewListener
import java.lang.Exception
import org.kimp.witelokk.museum.app.R
import org.kimp.witelokk.museum.app.databinding.FragmentQuestBinding
import org.kimp.witelokk.museum.app.databinding.ViewQuestionBinding
import org.kimp.witelokk.museum.app.models.QuestionsViewModel
import org.kimp.witelokk.museum.app.serialization.QuestionType

class QuestFragment : Fragment() {
    private lateinit var questionsViewModel: QuestionsViewModel
    private lateinit var binding: FragmentQuestBinding

    private var openedQuestions: Int = 0
    private var skipped: Int = 0

    @SuppressLint("SetTextI18n")
    private val questionViewListener = ViewListener { position ->
        val questionView = ViewQuestionBinding.inflate(
            layoutInflater
        )

        // Set questions number
        questionView.qvNumberMsg.text = "${position + 1} / ${questionsViewModel.getQuestions().value!!.size}"

        // Get current question
        val question = questionsViewModel.getQuestions().value!![position]

        // Hide an image and set question's text
        if (question.image == "") questionView.qvQuestionImage.visibility = View.GONE
        else {
            Picasso.get().load(question.image).resize(0, 256)
                .into(questionView.qvQuestionImage, object: Callback{
                override fun onSuccess() { }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
        }

        questionView.qvQuestionTextMsg.text = "\t" + question.text

        // Hide useless ui elements
        if (question.type != QuestionType.QR_QUESTION) questionView.qvScanQrBtn.visibility = View.GONE
        if (question.type != QuestionType.TEXT_QUESTION) questionView.qvTextAnswerIl.visibility = View.GONE
        if (question.type != QuestionType.NUMBER_QUESTION) questionView.qvNumberAnswerIl.visibility = View.GONE

        if (question.type == QuestionType.QR_QUESTION) questionView.qvCheckBtn.visibility = View.GONE

        // Skip button
        questionView.qvSkipBtn.setOnClickListener {
            question.givenAnswer = question.correctAnswer
            skipped++

            openNextQuestion()
        }

        // Check btn
        questionView.qvCheckBtn.setOnClickListener {
            var answer: String = ""

            answer = if (question.type == QuestionType.NUMBER_QUESTION) questionView.qvNumberAnswerIt.text.toString()
            else questionView.qvTextAnswerIt.text.toString()

            answer.trim()
            question.givenAnswer = answer

            if (answer == question.correctAnswer) {
                Snackbar.make(binding.root, R.string.snack_correct, Snackbar.LENGTH_SHORT).show()
                openNextQuestion()
            } else {
                questionView.qvNumberAnswerIt.error = resources.getString(R.string.snack_error)
                questionView.qvTextAnswerIt.error = resources.getString(R.string.snack_error)
            }
        }

        // Restore answers
        questionView.qvNumberAnswerIt.setText(question.givenAnswer)
        questionView.qvTextAnswerIt.setText(question.givenAnswer)

        // Autosave answers
        listOf(questionView.qvTextAnswerIt, questionView.qvNumberAnswerIt).forEach { et ->
            et.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    question.givenAnswer = p0.toString().trim()
                }

            })
        }

        // Check answered
        if (question.givenAnswer == question.correctAnswer) {
            questionView.qvSkipBtn.visibility = View.GONE
            questionView.qvCheckBtn.visibility = View.GONE

            questionView.qvTextAnswerIt.isEnabled = false
            questionView.qvNumberAnswerIt.isEnabled = false
        }

        questionView.qvScanQrBtn.setOnClickListener {
            val options = ScanOptions()
            options.setBeepEnabled(false)
            options.setOrientationLocked(true)

            qrResultLauncher.launch(options)
        }

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
                openedQuestions = 1

                binding.qfQuestionsCarousel.setViewListener(questionViewListener)
                binding.qfQuestionsCarousel.pageCount = openedQuestions

            }
        }

        binding.qfMapBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_questFragment_to_mapFragment)
        }

        binding.qfBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private val qrResultLauncher = registerForActivityResult(ScanContract()) {
        result ->
        if (result.contents != null) {
            val data = result.contents.toString().trim()
            questionsViewModel.getQuestions().value!!.get(openedQuestions - 1)
                .givenAnswer = data

            if (data == questionsViewModel.getQuestions().value!!.get(openedQuestions - 1).correctAnswer) {
                Snackbar.make(binding.root, R.string.snack_correct, Snackbar.LENGTH_SHORT).show()
                openNextQuestion()
                return@registerForActivityResult
            }
        }
        Snackbar.make(binding.root, R.string.snack_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun openNextQuestion() {
        if (openedQuestions == questionsViewModel.getQuestions().value!!.size) {
            val bundle = Bundle()
            bundle.putInt("total", questionsViewModel.getQuestions().value!!.size)
            bundle.putInt("skip", skipped)

            findNavController()
                .navigate(R.id.action_questFragment_to_resultFragment, bundle)

            return
        }

        binding.qfQuestionsCarousel.pageCount = openedQuestions + 1
        openedQuestions += 1
        binding.qfQuestionsCarousel.currentItem  = openedQuestions
    }
}