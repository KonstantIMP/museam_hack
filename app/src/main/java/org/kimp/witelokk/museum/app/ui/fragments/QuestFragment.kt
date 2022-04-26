package org.kimp.witelokk.museum.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.kimp.witelokk.museum.app.databinding.FragmentQuestBinding

class QuestFragment : Fragment() {
    private lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)

        return binding.root
    }
}