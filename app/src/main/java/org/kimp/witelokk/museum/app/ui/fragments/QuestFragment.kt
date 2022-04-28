package org.kimp.witelokk.museum.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.kimp.witelokk.museum.app.databinding.FragmentQuestBinding
import org.kimp.witelokk.museum.app.models.QuestionsViewModel

class QuestFragment : Fragment() {
    private lateinit var questionsViewModel: QuestionsViewModel
    private lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)

        questionsViewModel = ViewModelProvider(this)[QuestionsViewModel::class.java]
        questionsViewModel.loadQuestions(resources)

        return binding.root
    }
}